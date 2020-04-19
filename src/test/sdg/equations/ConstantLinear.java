package sdg.equations;

import sdg.Equation;

/**
 * Represents the SDE:
 *  dX(t) = aX(t) dt + bX(t) dW(t)
 */
public class ConstantLinear extends Equation {
    private double a;
    private double b;

    public ConstantLinear(double a, double b, double x0, double t0, double tN) {
        super(x0, t0, tN);

        this.a = a;
        this.b = b;
    }

    @Override
    public double drift(double X, double t)
    {
        return this.a * X;
    }

    @Override
    public double driftPrime(double X, double t)
    {
        return this.a;
    }

    @Override
    public double diffusion(double X, double t)
    {
        return this.b * X;
    }

    @Override
    public double diffusionPrime(double X, double t)
    {
        return this.b;
    }

    @Override
    public double diffusionDoublePrime(double X, double t)
    {
        return 0;
    }

    @Override
    public double exactSolution(double t, double totalNoise){
        return this.x0 * Math.exp(
                (this.a - 0.5 * Math.pow(this.b, 2)) * t +
                this.b*totalNoise
        );
    }
}
