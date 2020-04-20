package sdg;

import Jama.Matrix;

/**
 * SDG class holding the main logic for solving SDG approximations for a specified
 * ordinary SDE, simulated noise, specified mesh, and polynomial degree.
 */
public class SDG {

    /**
     * Solves the specified ordinary SDE with given simulated noise, mesh, and polynomial degree.
     *
     * @param equation Instance of main.sdg.Equation -- defining the ordinary SDE to solve.
     * @param noise Instance of main.sdg.Noise -- simulated Brownian motion on the mesh.
     * @param mesh Instance of main.sdg.Mesh representing the one-dimensional partitioning of your domain.
     * @param polynomialDegree Degree of the polynomial approximation.
     * @return ApproxGlobal instance which contains a set of ApproxLocal for every element in the mesh.
     */
    public ApproxGlobal Solve(Equation equation, Noise noise, Mesh mesh, int polynomialDegree)
    {
        // The global approximation object will hold all of the local approximations.
        ApproxGlobal approxGlobal = new ApproxGlobal(mesh);

        // Initialize boundary. We pass in element 0 (when it should actually be element -1).
        // This isn't used and is done for convenience.
        ApproxLocal priorApprox = new ApproxLocal(mesh.elements[0], polynomialDegree);
        priorApprox.coefficients.set(0,0,equation.x0);
        
        // Fit the polynomial on each element.
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

    /**
     * Use Newton's method to solve the system of non-linear test.sdg.equations required to obtain the SDG approximation.
     *
     * @param equation Instance of main.sdg.Equation (the ordinary SDE to solve).
     * @param approx Instance of main.sdg.ApproxLocal representing the element we are solving on. e.g. [t2, t3]
     * @param priorApprox Instance of main.sdg.ApproxLocal representing the solution on the previous element
     *                    in the mesh. e.g. [t1, t2]
     */
    private void SolveElement(Equation equation, ApproxLocal approx, ApproxLocal priorApprox)
    {
        // To solve the non-linear system we have to minimize the infinite norm of the vector f.
        Matrix f = new Matrix(approx.polynomialDegree+1, 1);

        // The Jacobian matrix contains first derivative information.
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

    /**
     *  Compute the matrices f and the Jacobian for this iteration of Newton's method.
     *
     *  Note: We compute f and the Jacobian in the same loops for
     *  efficiency reasons (even though its harder to follow the code).
     *
     * @param f Matrix (vector) representing the function we are minimizing.
     * @param jacobian Matrix with first derivative information.
     * @param equation Instance of main.sdg.Equation (the ordinary SDE to solve).
     * @param approx Instance of main.sdg.ApproxLocal representing the element we are solving on. e.g. [t2, t3]
     * @param priorApprox Instance of main.sdg.ApproxLocal representing the solution on the previous element
     *                    in the mesh. e.g. [t1, t2]
     */
    private void PrepareNewton(Matrix f, Matrix jacobian, Equation equation, 
            ApproxLocal approx, ApproxLocal priorApprox)
    {
        // For the Jacobian, the flux terms are constant
        double jacobianFlux1 = -1;
        double jacobianFlux2 = 0;

        // Evaluate the current value of X at Gaussian nodes. Since the drift and diffusion functions are not
        // necessarily additive with respect to the ansatz, we have to compute X at the Gaussian nodes in a
        // different loop than computing the integral.
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

            // Use a Gaussian quadrature to compute integral2.
            for (int gaussianIndex = 0; gaussianIndex < Quadrature.gaussianNodeCount; gaussianIndex++)
            {
                double node = Quadrature.gaussianNodes[gaussianIndex];
                double nodeMapped = 0.5*(node*(approx.element.upperEndpoint - approx.element.lowerEndpoint) +
                        (approx.element.upperEndpoint + approx.element.lowerEndpoint));

                double drift = equation.drift(xAtNodes[gaussianIndex], nodeMapped);
                double diffusion = equation.diffusion(xAtNodes[gaussianIndex], nodeMapped);
                double diffusionPrime = equation.diffusionPrime(xAtNodes[gaussianIndex], nodeMapped);

                double integrand = (drift - 0.5 * diffusion * diffusionPrime + diffusion*approx.wongZakai) *
                        Legendre.basis(testFunctionIndex, node);

                integral2 += (approx.element.length / 2) * integrand * Quadrature.gaussianWeights[gaussianIndex];
            }

            for (int basisIndex = 0; basisIndex < approx.polynomialDegree + 1; basisIndex++)
            {
                // Integral1 can be simplified using Legendre properties, since there is a known formula for
                // integral p_i * p'_j over the domain [-1, 1]. By using this property, we can avoid computing
                // derivatives of Legendre polynomials.
                double legendreShortcut = (1 - Math.pow(-1, basisIndex + testFunctionIndex));
                if (testFunctionIndex != 0 && basisIndex <= testFunctionIndex)
                    integral1 += approx.coefficients.get(basisIndex, 0) * legendreShortcut;

                // We comment out the function evaluation at t=1 because it is always = 1 by definition.
                // Leaving the code in so the computations are easier to follow.
                flux1 -= approx.coefficients.get(basisIndex, 0); //* Legendre.basis(testFunctionIndex, 1);
                flux2 += priorApprox.coefficients.get(basisIndex, 0) * Legendre.basis(testFunctionIndex, -1);

                // Jacobian integrals are different for each basis/test function combination.
                double jacobianIntegral1 = 0;
                if (testFunctionIndex != 0 && basisIndex <= testFunctionIndex)
                    jacobianIntegral1 = legendreShortcut;

                // Use a Gaussian quadrature for the second jacobian integral.
                double jacobianIntegral2 = 0;
                for (int gaussianIndex = 0; gaussianIndex < Quadrature.gaussianNodeCount; gaussianIndex++)
                {
                    double node = Quadrature.gaussianNodes[gaussianIndex];
                    double nodeMapped = 0.5*(node*(approx.element.upperEndpoint - approx.element.lowerEndpoint) +
                            (approx.element.upperEndpoint + approx.element.lowerEndpoint));

                    double driftPrime = equation.driftPrime(xAtNodes[gaussianIndex], nodeMapped);
                    double diffusion = equation.diffusion(xAtNodes[gaussianIndex], nodeMapped);
                    double diffusionPrime = equation.diffusionPrime(xAtNodes[gaussianIndex], nodeMapped);
                    double diffusionDoublePrime = equation.diffusionDoublePrime(xAtNodes[gaussianIndex], nodeMapped);

                    // Using the chain and product rules.
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
