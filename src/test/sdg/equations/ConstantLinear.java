package sdg.equations;

import sdg.Equation;


public class ConstantLinear extends Equation {
    private double constantDrift;
    private double constantDiffusion;

    public ConstantLinear(double constantDrift, double constantDiffusion, double x0, double t0,double tN) {
        super(x0, t0, tN);

        this.constantDrift = constantDrift;
        this.constantDiffusion = constantDiffusion;
    }

    @Override
    public double drift(double X, double t)
    {
        return this.constantDrift;
    }

    @Override
    public double driftPrime(double X, double t)
    {
        return 0;
    }

    @Override
    public double diffusion(double X, double t)
    {
        return this.constantDiffusion;
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
        return this.x0*Math.exp(
                (this.constantDrift-0.5*Math.pow(this.constantDiffusion, 2))*t +
                this.constantDiffusion*totalNoise
        );
    }
}
