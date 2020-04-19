package sdg.equations;

import sdg.Equation;

/**
 * Represents the SDE:
 *  dX(t) = -X(t)/(1+t) dt + 1/(1+t) dW(t)
 */
public class Linear2 extends Equation {

    public Linear2(double x0, double t0, double tN) {
        super(x0, t0, tN);
    }
    
    @Override
    public double drift(double X, double t)
    {
        return -X / (1 + t);
    }
        
    @Override
    public double driftPrime(double X, double t)
    {
        return -1 / (1 + t);
    }
    
    @Override
    public double diffusion(double X, double t)
    {
        return 1 / (1 + t);
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
        return totalNoise / (1 + t);
    }
}
