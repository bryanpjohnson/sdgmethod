package solutions;
import sdg.Equation;
import sdg.Exact;


public class Solution6 extends Exact {

    double alpha = 0.1;
    double beta = 0.5;
                    
    @Override
    public double terminal(Equation equation, double totalNoise)
    {
        return equation.x0/Math.sqrt(1+equation.tN) + (beta/Math.sqrt(1+equation.tN))*(equation.tN + alpha*totalNoise);
    }
}
