package sdg;


public class Legendre {
    
    public static double basis(int degree, double x)
    {
        //allocate enough room for at least the first 2
        double[] p = new double[degree+2];
        p[0] = 1;
        p[1] = x;
        
        //generate using Bonnet's recursion formula
        for(int n = 1; n < degree; n++)
        {
            p[n+1] = ((2*n+1)*x*p[n] - n*p[n-1])/(n+1);
        }
                
        return p[degree];
    }
}
