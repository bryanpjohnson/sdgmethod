package solutions;
import sdg.Equation;
import sdg.Exact;

/**
 *
 * @author bryan.johnson
 */
public class Solution3 extends Exact {

    @Override
    public double terminal(Equation equation, double totalNoise)
    {
        return Math.exp(equation.tN) - 1 + totalNoise;
    }
}
