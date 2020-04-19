package sdg.equations;

import sdg.Equation;

/**
 * Represents the SDE:
 *  dX(t) = [-sin(X(t)) * cos(X(t))^3 ] dt + cos(X(t))^2 dW(t)
 */
public class NonLinear2 extends Equation {

    public NonLinear2(double x0, double t0, double tN) {
        super(x0, t0, tN);
    }

    @Override
    public double drift(double X, double t)
    {
        return -Math.sin(X) * Math.pow(Math.cos(X), 3);
    }
        
    @Override
    public double driftPrime(double X, double t)
    {
        return -Math.pow(Math.cos(X), 2) * (1 - 2 * Math.cos(2 * X));
    }
    
    @Override
    public double diffusion(double X, double t)
    {
        return Math.pow(Math.cos(X), 2);
    }
    
    @Override    
    public double diffusionPrime(double X, double t)
    {
        return -2 * Math.sin(X) * Math.cos(X);
    }
    
    @Override   
    public double diffusionDoublePrime(double X, double t)
    {
        return -2 * Math.cos(2 * X);
    }

    @Override
    public double exactSolution(double t, double totalNoise){
        return Math.atan(Math.tan(this.x0) + totalNoise);
    }
}
