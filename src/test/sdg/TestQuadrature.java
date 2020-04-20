package sdg;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class TestQuadrature {
    @Test
    public void test() {
        Quadrature q = new Quadrature();

        // integrate x*sin(x + e^x) from -1 to 1
        double integral = 0;
        for(int i = 0; i < q.gaussianNodeCount; i++){
            integral += integrand(q.gaussianNodes[i])*q.gaussianWeights[i];
        }

        assertEquals(integral, 0.24088467574851374, 10e-15);
    }

    private double integrand(double x){
        return x*Math.sin(x + Math.exp(x));
    }
}
