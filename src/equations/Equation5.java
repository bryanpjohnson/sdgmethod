package equations;
import sdg.Equation;


public class Equation5 extends Equation {

    public Equation5() {
        // x0, t0, tN
        super(0, 0, 3);
    }
    
    @Override
    public double drift(double t, double X)
    {
        return -1;
    }
        
    @Override
    public double driftPrime(double t, double X)
    {
        return 0;
    }
    
    @Override
    public double diffusion(double t, double X)
    {
        return 1;
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
