package equations;
import sdg.Equation;

/**
 *
 * @author bryan.johnson
 */
public class Equation7 extends Equation {

    public Equation7() {
        // x0, t0, tN
        super(1, 0, Math.PI);
    }
    
    @Override
    public double drift(double t, double X)
    {
        return Math.pow(Math.sin(X), 3)*Math.cos(X) - Math.pow(Math.sin(X), 2);
    }
        
    @Override
    public double driftPrime(double t, double X)
    {
        return Math.sin(X)*(Math.sin(3*X) - 2*Math.cos(X));
    }
    
    @Override
    public double diffusion(double t, double X)
    {
        return  -Math.pow(Math.sin(X), 2);
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
