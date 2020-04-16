package equations;
import sdg.Equation;


public class Equation1 extends Equation {

    public Equation1() {
        // x0, t0, tN
        super(0, 0, 1);
    }
    
    @Override
    public double drift(double t, double X)
    {
        return -X/(1+t);
    }
        
    @Override
    public double driftPrime(double t, double X)
    {
        return -1/(1+t);
    }
    
    @Override
    public double diffusion(double t, double X)
    {
        return 1/(1+t);
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
