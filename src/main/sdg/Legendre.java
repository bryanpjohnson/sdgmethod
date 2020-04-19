package sdg;

/**
 * Represents a Legendre polynomial. These are the basis functions for
 * our polynomial approximations on each finite element of the SDG method.
 *
 * https://en.wikipedia.org/wiki/Legendre_polynomials
 *
 * We use this orthogonal polynomial basis because of their desirable computational properties.
 */
public class Legendre {

    /**
     * For a Legendre polynomial (L) of specified degree, compute L(x) using Bonnet's recursion formula.
     *
     * @param degree The degree of polynomial you want to evaluate.
     * @param x The input value of the function L(x).
     * @return Double - the evaluated Legendre polynomial at point x.
     */
    public static double basis(int degree, double x)
    {
        // Allocate enough room for at least the first 2 polynomials.
        double[] p = new double[degree+2];
        p[0] = 1;
        p[1] = x;
        
        // Generate using Bonnet's recursion formula.
        for(int n = 1; n < degree; n++)
        {
            p[n+1] = ((2*n+1)*x*p[n] - n*p[n-1])/(n+1);
        }
                
        return p[degree];
    }
}
