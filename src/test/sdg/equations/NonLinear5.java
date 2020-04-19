package sdg.equations;
import sdg.Equation;


public class NonLinear5 extends Equation {

    public NonLinear5(double x0, double t0, double tN) {
        super(x0, t0, tN);
    }
    
    @Override
    public double drift(double X, double t)
    {
        return Math.pow(Math.sin(X), 3)*Math.cos(X) - Math.pow(Math.sin(X), 2);
    }
        
    @Override
    public double driftPrime(double X, double t)
    {
        return Math.sin(X)*(Math.sin(3*X) - 2*Math.cos(X));
    }
    
    @Override
    public double diffusion(double X, double t)
    {
        return  -Math.pow(Math.sin(X), 2);
    }
    
    @Override    
    public double diffusionPrime(double X, double t)
    {
        return -2*Math.sin(X)*Math.cos(X);
    }
    
    @Override   
    public double diffusionDoublePrime(double X, double t)
    {
        return -2*Math.cos(2*X);
    }

    @Override
    public double exactSolution(double t, double totalNoise) {
        return Math.PI/2 - Math.atan(1.0/Math.tan(this.x0) + t + totalNoise);
    }
}
