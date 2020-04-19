package sdg.equations;

import sdg.Equation;

/**
 * Represents the SDE:
 *  [2X(t)/(1+t) + (1+t)^2] dt + (1+t)^2 dW(t)
 */
public class Linear3 extends Equation {

    public Linear3(double x0, double t0, double tN) {
        super(x0, t0, tN);
    }
    
    @Override
    public double drift(double X, double t)
    {
        return 2 * X / (1 + t) + Math.pow(1 + t, 2);
    }
        
    @Override
    public double driftPrime(double X, double t)
    {
        return 2 / (1 + t);
    }
    
    @Override
    public double diffusion(double X, double t)
    {
        return Math.pow(1 + t, 2);
    }
    
    @Override    
    public double diffusionPrime(double X, double t)
    {
        return 0;
    }
    
    @Override   
    public double diffusionDoublePrime(double X, double t)
    {
        return 0;
    }

    @Override
    public double exactSolution(double t, double totalNoise) {
        return Math.pow(1 + t, 3) + Math.pow(1 + t, 2) * totalNoise;
    }
}
