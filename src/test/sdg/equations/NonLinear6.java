package sdg.equations;

import sdg.Equation;


/**
 * Represents the SDE:
 *  dX(t) = (1/2 X(t) + sqrt(X(t)^2 + 1)) dt + sqrt(X(t)^2 + 1) dW(t)
 */
public class NonLinear6 extends Equation {

    public NonLinear6(double x0, double t0, double tN) {
        super(x0, t0, tN);
    }
    
    @Override
    public double drift(double X, double t)
    {
        return 0.5*X + Math.sqrt(Math.pow(X, 2) + 1);
    }
        
    @Override
    public double driftPrime(double X, double t)
    {
        return 0.5 + X*Math.pow(Math.pow(X, 2) + 1, -0.5);
    }
    
    @Override
    public double diffusion(double X, double t)
    {
        return Math.sqrt(Math.pow(X, 2) + 1);
    }
    
    @Override    
    public double diffusionPrime(double X, double t)
    {
        return X*Math.pow(Math.pow(X, 2) + 1, -0.5);
    }
    
    @Override   
    public double diffusionDoublePrime(double X, double t)
    {
        return Math.pow(Math.pow(X, 2) + 1, -1.5);
    }


    @Override
    public double exactSolution(double t, double totalNoise) {
        return Math.sinh(t + totalNoise);
    }
}
