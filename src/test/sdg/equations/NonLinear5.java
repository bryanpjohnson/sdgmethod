package sdg.equations;

import sdg.Equation;

/**
 * Represents the SDE:
 *  dX(t) = -(a + b^2 X(t))(1 - X(t)^2) dt + b(1 - X(t)^2) dW(t)
 */
public class NonLinear5 extends Equation {
    private double a;
    private double b;

    public NonLinear5(double a, double b, double x0, double t0, double tN) {
        super(x0, t0, tN);

        this.a = a;
        this.b = b;
    }

    @Override
    public double drift(double X, double t)
    {
        return -(this.a + Math.pow(this.b, 2) * X) * (1-Math.pow(X, 2));
    }

    @Override
    public double driftPrime(double X, double t)
    {
        return 2 *this.a * X + Math.pow(this.b, 2) * (3 * Math.pow(X, 2) - 1);
    }

    @Override
    public double diffusion(double X, double t)
    {
        return this.b * (1-Math.pow(X, 2));
    }

    @Override
    public double diffusionPrime(double X, double t)
    {
        return -2 * this.b * X;
    }

    @Override
    public double diffusionDoublePrime(double X, double t)
    {
        return -2 * this.b;
    }

    @Override
    public double exactSolution(double t, double totalNoise){
        double commonValue = (1 + this.x0) * Math.exp(-2 * this.a * t + 2 * this.b * totalNoise);
        double numerator = commonValue + this.x0 - 1;
        double denominator = commonValue + 1 - this.x0;

        return numerator/denominator;
    }
}
