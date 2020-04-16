package equations;
import sdg.Equation;


public class Equation2 extends Equation {

    public Equation2() {
        // x0, t0, tN
        super(0.5, 0, 1);
    }
    
    @Override
    public double drift(double t, double X)
    {
        return -Math.sin(X)*Math.pow(Math.cos(X), 3);
    }
        
    @Override
    public double driftPrime(double t, double X)
    {
        return -Math.pow(Math.cos(X), 2)*(1-2*Math.cos(2*X));
    }
    
    @Override
    public double diffusion(double t, double X)
    {
        return Math.pow(Math.cos(X), 2);
    }
    
    @Override    
    public double diffusionPrime(double t, double X)
    {
        return -2*Math.sin(X)*Math.cos(X);
    }
    
    @Override   
    public double diffusionDoublePrime(double t, double X)
    {
        return -2*Math.cos(2*X);
    }
}
