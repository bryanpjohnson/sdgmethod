package solutions;
import sdg.Equation;
import sdg.Exact;


public class Solution3 extends Exact {

    @Override
    public double terminal(Equation equation, double totalNoise)
    {
        return Math.exp(equation.tN) - 1 + totalNoise;
    }
}
