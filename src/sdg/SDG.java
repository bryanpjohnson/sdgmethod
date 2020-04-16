package sdg;

import Jama.Matrix;


public class SDG {

    public ApproxGlobal Solve(Equation equation, Noise noise, Mesh mesh, int polynomialDegree)
    {
        // The global approximation object will hold all of the local approximations.
        ApproxGlobal approxGlobal = new ApproxGlobal(mesh);

        // Initialize boundary. We pass in element 0 (when it should actually be element -1)
        // but this isn't used and we do this for convenience.
        ApproxLocal priorApprox = new ApproxLocal(mesh.elements[0], polynomialDegree);
        priorApprox.coefficients.set(0,0,equation.x0);
        
        // Fit the polynomial on each element
        for (int k = 0; k < mesh.numberElements; k++)
        {
            ApproxLocal approx = new ApproxLocal(mesh.elements[k], polynomialDegree);
            approx.wongZakai = noise.discreteNoise[k] / approx.element.length;
            approx.coefficients = (Matrix) priorApprox.coefficients.clone();

            SolveElement(equation, approx, priorApprox);
            approxGlobal.localApproximations[k] = approx;
            priorApprox = approx;
        }

        return approxGlobal;
    }
    
    private void SolveElement(Equation equation, ApproxLocal approx, ApproxLocal priorApprox)
    {
        //minimize f for approx
        Matrix f = new Matrix(approx.polynomialDegree+1, 1);
        Matrix jacobian = new Matrix(approx.polynomialDegree+1, approx.polynomialDegree+1);

        double error;
        double tolerance = 10e-13;
        int max_iterations = 1000;

        int iteration = 0;
        do
        {
            PrepareNewton(f, jacobian, equation, approx, priorApprox);
            Matrix increment = jacobian.solve(f.times(-1));
            approx.coefficients.plusEquals(increment);
            error = f.normInf();

            iteration++;
        } while (error  > tolerance && iteration < max_iterations);
    }
    
    // We compute the objective function and the jacobian in the same loops for
    // efficiency reasons (even though its harder to follow the code).
    private void PrepareNewton(Matrix f, Matrix jacobian, Equation equation, 
            ApproxLocal approx, ApproxLocal priorApprox)
    {
	// For the jacobian, the flux terms are constant
	double jacobianFlux1 = -1;
	double jacobianFlux2 = 0;

	// Evaluate the current value of X at gaussian nodes.
	// Since the drift and diffusion functions are not necessarily additive with respect to the ansatz,
	// we have to compute X at the gaussian nodes in a different loop than computing the integral.
	double xAtNodes[] = new double[Quadrature.gaussianNodeCount];

	for (int gaussianIndex = 0; gaussianIndex < Quadrature.gaussianNodeCount; gaussianIndex++)
	{
            double node = Quadrature.gaussianNodes[gaussianIndex];
            for (int basisIndex = 0; basisIndex < approx.polynomialDegree + 1; basisIndex++)
            {
                 xAtNodes[gaussianIndex] += approx.coefficients.get(basisIndex,0) * 
                         Legendre.basis(basisIndex, node);
            }
	}

	// Integrate the terms for each test function.
	for (int testFunctionIndex = 0; testFunctionIndex < approx.polynomialDegree + 1; testFunctionIndex++)
	{
            double integral1 = 0;
            double integral2 = 0;
            double flux1 = 0;
            double flux2 = 0;

            // Use a gaussian quadrature to compute integral2.
            for (int gaussianIndex = 0; gaussianIndex < Quadrature.gaussianNodeCount; gaussianIndex++)
            {
                double node = Quadrature.gaussianNodes[gaussianIndex];
                double nodeMapped = 0.5*(node*(approx.element.upperEndpoint - approx.element.lowerEndpoint) +
                        (approx.element.upperEndpoint + approx.element.lowerEndpoint));

                double drift = equation.drift(nodeMapped, xAtNodes[gaussianIndex]);
                double diffusion = equation.diffusion(nodeMapped, xAtNodes[gaussianIndex]);
                double diffusionPrime = equation.diffusionPrime(nodeMapped, xAtNodes[gaussianIndex]);

                double integrand = (drift - 0.5 * diffusion * diffusionPrime + diffusion*approx.wongZakai) * 
                        Legendre.basis(testFunctionIndex, node);

                integral2 += (approx.element.length / 2) * integrand * Quadrature.gaussianWeights[gaussianIndex];
            }

            for (int basisIndex = 0; basisIndex < approx.polynomialDegree + 1; basisIndex++)
            {
                // Integral1 can be simplified using legendre properties since there is a known formula for integral p_i * p'_j
                // over the domain [-1, 1].  By using  this we can avoid computing derivatives of Legendre polynomials.
                if (testFunctionIndex != 0 && basisIndex <= testFunctionIndex)
                    integral1 += approx.coefficients.get(basisIndex, 0) * (1 - Math.pow(-1, basisIndex + testFunctionIndex));

                flux1 -= approx.coefficients.get(basisIndex, 0); //* Legendre.basis(testFunctionIndex, 1);  <--- always 1 by definition
                flux2 += priorApprox.coefficients.get(basisIndex, 0) * Legendre.basis(testFunctionIndex, -1);
                
                // jacobian integrals are different for each basis/test function combination
                double jacobianIntegral1 = 0;
                if (testFunctionIndex != 0 && basisIndex <= testFunctionIndex)
                    jacobianIntegral1 = (1 - Math.pow(-1, basisIndex + testFunctionIndex));

                // Use a gaussian quadrature to jacobian_integral2.
                double jacobianIntegral2 = 0;
                for (int gaussianIndex = 0; gaussianIndex < Quadrature.gaussianNodeCount; gaussianIndex++)
                {
                    double node = Quadrature.gaussianNodes[gaussianIndex];
                    double nodeMapped = 0.5*(node*(approx.element.upperEndpoint - approx.element.lowerEndpoint) +
                            (approx.element.upperEndpoint + approx.element.lowerEndpoint));

                    double driftPrime = equation.driftPrime(nodeMapped, xAtNodes[gaussianIndex]);
                    double diffusion = equation.diffusion(nodeMapped, xAtNodes[gaussianIndex]);
                    double diffusionPrime = equation.diffusionPrime(nodeMapped, xAtNodes[gaussianIndex]);
                    double diffusionDoublePrime = equation.diffusionDoublePrime(nodeMapped, xAtNodes[gaussianIndex]);

                    // Using the chain and product rules
                    double jacobianIntegrand = (driftPrime - 0.5 * (diffusionPrime*diffusionPrime + 
                            diffusion*diffusionDoublePrime) + diffusionPrime * approx.wongZakai) * 
                            Legendre.basis(basisIndex, node) * Legendre.basis(testFunctionIndex, node);

                    jacobianIntegral2 += (approx.element.length / 2) * jacobianIntegrand * 
                            Quadrature.gaussianWeights[gaussianIndex];
                }

                jacobian.set(testFunctionIndex, basisIndex, jacobianIntegral1 + jacobianIntegral2 + 
                        jacobianFlux1 + jacobianFlux2);
            }

            f.set(testFunctionIndex, 0, integral1 + integral2 + flux1 + flux2);
        }    
    }
}
