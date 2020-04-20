package sdg;

/**
 * Represents a one-dimensional ordinary SDE of the form:
 *  X(t) = μ(X(t), t) dt + σ(X(t), t) dB(t)
 *
 *  This abstract class should be implemented for the equation you wish to approximate.
 *
 *  In this above SDE:
 *      - μ is the drift function to implement.
 *      - σ is the diffusion function to implement.
 *
 *  You must also implement:
 *      - the driftPrime function -- the first derivative of μ with respect to X.
 *      - the diffusionPrime function -- the first derivative of σ with respect to X.
 *      - the diffusionDoublePrime function -- the second derivative of σ with respect to X.
 *
 *  If your equation has a known (exact) solution, override the exactSolution function.
 */
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

    public abstract double drift(double X, double t);
    public abstract double driftPrime(double X, double t);
    public abstract double diffusion(double X, double t);
    public abstract double diffusionPrime(double X, double t);
    public abstract double diffusionDoublePrime(double X, double t);

    /**
     * Implement this if your SDE has a known closed form solution. This can be used for experiments and testing.
     * @param t the time t to evaluate the solution at.
     * @param totalNoise the accumulated simulated noise at time t.
     * @return double X(t).
     */
    public double exactSolution(double t, double totalNoise) {
        throw new UnsupportedOperationException();
    };
}