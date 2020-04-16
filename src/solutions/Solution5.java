package solutions;
import sdg.Equation;
import sdg.Exact;


public class Solution5 extends Exact {

    @Override
    public double terminal(Equation equation, double totalNoise)
    {
        return Math.exp(-1.5*equation.tN + totalNoise);
    }
}
