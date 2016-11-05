package solutions;
import sdg.Equation;
import sdg.Exact;

/**
 *
 * @author bryan.johnson
 */
public class Solution1 extends Exact {

    @Override
    public double terminal(Equation equation, double totalNoise)
    {
        return totalNoise/(1+equation.tN);
    }
}
