package solutions;
import sdg.Equation;
import sdg.Exact;


public class Solution1 extends Exact {

    @Override
    public double terminal(Equation equation, double totalNoise)
    {
        return totalNoise/(1+equation.tN);
    }
}
