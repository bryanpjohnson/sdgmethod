package solutions;
import sdg.Equation;
import sdg.Exact;

/**
 *
 * @author bryan.johnson
 */
public class Solution2 extends Exact {

    @Override
    public double terminal(Equation equation, double totalNoise)
    {
        return Math.atan(Math.tan(equation.x0)+totalNoise);
    }
}
