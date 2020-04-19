package sdg.equations;

import sdg.Equation;

/**
 * Represents the SDE:
 *  dX(t) = [beta/sqrt(1+t) - X(t)/(2*(1-t))] dt - (alpha * beta)/sqrt(1+t) dW(t)
 */
public class Linear4 extends Equation {
    double alpha;
    double beta;

    public Linear4(double alpha, double beta, double x0, double t0, double tN) {
        super(x0, t0, tN);

        this.alpha = alpha;
        this.beta = beta;
    }
    
    @Override
    public double drift(double X, double t)
    {
        return this.beta/Math.sqrt(1 + t) - X / (2 * (1 + t));
    }
        
    @Override
    public double driftPrime(double X, double t)
    {
        return -1 / (2 * (1 + t));
    }
    
    @Override
    public double diffusion(double X, double t)
    {
        return this.alpha * this.beta / Math.sqrt(1 + t);
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
        return this.x0 / Math.sqrt(1 + t) + (this.beta / Math.sqrt(1 + t)) *
                (t + this.alpha * totalNoise);
    }
}
