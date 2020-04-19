package sdg;

import Jama.Matrix;

/**
 * Represents the SDE approximation on 1 element of the mesh.
 */
public class ApproxLocal {
    
    Element element;
    Matrix coefficients;
    int polynomialDegree;
    double wongZakai;

    /**
     * Constructor for ApproxLocal.
     * @param element Instance of the main.sdg.Element class.
     * @param polynomialDegree Degree of the polynomial to use for approximating this element.
     */
    public ApproxLocal(Element element, int polynomialDegree)
    {
        this.element = element;
        this.coefficients = new Matrix(polynomialDegree+1, 1);
        this.polynomialDegree = polynomialDegree;
        this.wongZakai = 0;
    }

    /**
     * Gets the value of the approximation at the terminal point of the element:
     *  i.e., a(T) if this element is defined on [t, T].
     *
     *  Note that we are using the convenient property of Legendre polynomials where L(T) = 1.
     *  Because of this, we can just sum the coefficients.
     *
     * @return Double value of the approximation at the terminal point of the element.
     */
    public double terminal()
    {
        double approxT = 0;

        for(int z = 0; z < this.polynomialDegree + 1; z++)
        {
            approxT += this.coefficients.get(z, 0);
        }

        return approxT;
    }
}