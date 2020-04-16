package equations;
import sdg.Equation;


public class Equation6 extends Equation {

    double alpha = 0.1;
    double beta = 0.5;
    
    public Equation6() {
        // x0, t0, tN
        super(1, 0, 3);
    }
    
    @Override
    public double drift(double t, double X)
    {
        return beta/Math.sqrt(1+t) - X/(2*(1+t));
    }
        
    @Override
    public double driftPrime(double t, double X)
    {
        return -1/(2*(1+t));
    }
    
    @Override
    public double diffusion(double t, double X)
    {
        return alpha*beta/Math.sqrt(1+t);
    }
    
    @Override    
    public double diffusionPrime(double t, double X)
    {
        return 0;
    }
    
    @Override   
    public double diffusionDoublePrime(double t, double X)
    {
        return 0;
    }
}
