package sdg.equations;

import sdg.Equation;

/**
 * Represents the SDE:
 *  dX(t) = -a^2 X(t)(1-X(t)) dt + a(1-X(t)^2) dW(t)
 */
public class NonLinear4 extends Equation {
    double a;

    public NonLinear4(double a, double x0, double t0, double tN) {
        super(x0, t0, tN);
        this.a = a;
    }
    
    @Override
    public double drift(double X, double t)
    {
        return -Math.pow(this.a, 2)*X*(1-Math.pow(X, 2));

    }
        
    @Override
    public double driftPrime(double X, double t)
    {
        return Math.pow(this.a, 2)*X*(1-3*Math.pow(X, 2));
    }
    
    @Override
    public double diffusion(double X, double t)
    {
        return this.a *(1-Math.pow(X, 2));
    }
    
    @Override    
    public double diffusionPrime(double X, double t)
    {
        return -2*this.a *X;
    }
    
    @Override   
    public double diffusionDoublePrime(double X, double t)
    {
        return -2*this.a;
    }


    @Override
    public double exactSolution(double t, double totalNoise) {
        return Math.tanh(this.a * totalNoise + arctanh(this.x0));
    }

    /**
     * Hyperbolic arctangent.
     * @param x Point to compute the arctanh.
     * @return Double
     */
    double arctanh(double x) {
        return 0.5*Math.log((1.0 + x) / (1.0 - x));
    }
}
