package sdg.equations;

import sdg.Equation;

/**
 * Represents the SDE:
 *  dX(t) = a dt + b sqrt(X(t)) dW(t)
 */
public class NonLinear1 extends Equation {

    double a;
    double b;

    public NonLinear1(double a, double b, double x0, double t0, double tN) {
        super(x0, t0, tN);
        this.a = a;
        this.b = b;
    }
    
    @Override
    public double drift(double X, double t)
    {
        return this.a;
    }
        
    @Override
    public double driftPrime(double X, double t)
    {
        return 0;
    }
    
    @Override
    public double diffusion(double X, double t)
    {
        return this.b * Math.sqrt(X);
    }
    
    @Override    
    public double diffusionPrime(double X, double t)
    {
        return 0.5 * this.b * Math.pow(X, -0.5);
    }
    
    @Override   
    public double diffusionDoublePrime(double X, double t)
    {
        return -0.25 * this.b * Math.pow(X, -1.5);
    }

    @Override
    public double exactSolution(double t, double totalNoise) {
        return Math.pow(totalNoise + Math.sqrt(this.x0), 2);
    }
}
