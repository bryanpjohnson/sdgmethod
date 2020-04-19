package sdg.equations;
import sdg.Equation;


public class Linear1 extends Equation {

    public Linear1(double x0, double t0, double tN) {
        super(x0, t0, tN);
    }
    
    @Override
    public double drift(double X, double t)
    {
        return Math.exp(t);
    }
        
    @Override
    public double driftPrime(double X, double t)
    {
        return 0;
    }
    
    @Override
    public double diffusion(double X, double t)
    {
        return 1;
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
    public double exactSolution(double t, double totalNoise){
        return Math.exp(t) - 1 + totalNoise;
    }
}
