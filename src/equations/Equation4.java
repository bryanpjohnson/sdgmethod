package equations;
import sdg.Equation;

/**
 *
 * @author bryan.johnson
 */
public class Equation4 extends Equation {

    public Equation4() {
        // x0, t0, tN
        super(1, 0, 1);
    }
    
    @Override
    public double drift(double t, double X)
    {
        return 2*X/(1+t) + Math.pow(1+t, 2);
    }
        
    @Override
    public double driftPrime(double t, double X)
    {
        return 2/(1+t);
    }
    
    @Override
    public double diffusion(double t, double X)
    {
        return Math.pow(1+t, 2);
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
