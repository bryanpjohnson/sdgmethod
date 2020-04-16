package sdg;

import Jama.Matrix;


public class ApproxLocal {
    
    Element element;
    Matrix coefficients;
    int polynomialDegree;
    double wongZakai;
    
    public ApproxLocal(Element element, int polynomialDegree)
    {
        this.element = element;
        this.coefficients = new Matrix(polynomialDegree+1, 1);
        this.polynomialDegree = polynomialDegree;
        this.wongZakai = 0;
    }

    public double terminal()
    {
        double approxT = 0;
        for(int z = 0; z < this.polynomialDegree+1; z++)
        {
            approxT += this.coefficients.get(z, 0);
        }

        return approxT;
    }
}