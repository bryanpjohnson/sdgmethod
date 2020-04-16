package solutions;
import sdg.Equation;
import sdg.Exact;


public class Solution4 extends Exact {

    @Override
    public double terminal(Equation equation, double totalNoise)
    {
        return Math.pow(1+equation.tN, 3) + Math.pow(1+equation.tN, 2)*totalNoise;
    }
}
