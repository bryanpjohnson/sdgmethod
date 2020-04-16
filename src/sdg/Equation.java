package sdg;


public abstract class Equation {

    public double x0;
    public double t0;
    public double tN;
    
    public Equation(double x0, double t0, double tN)
    {
        this.x0 = x0;
        this.t0 = t0;
        this.tN = tN;
    };
    public abstract double drift(double t, double X);
    public abstract double driftPrime(double t, double X);
    public abstract double diffusion(double t, double X);
    public abstract double diffusionPrime(double t, double X);
    public abstract double diffusionDoublePrime(double t, double X);
}