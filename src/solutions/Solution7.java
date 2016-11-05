package solutions;
import sdg.Equation;
import sdg.Exact;

/**
 *
 * @author bryan.johnson
 */
public class Solution7 extends Exact {
             
    @Override
    public double terminal(Equation equation, double totalNoise)
    {
        return Math.PI/2 - Math.atan(1.0/Math.tan(equation.x0) + equation.tN + totalNoise);
    }
}
