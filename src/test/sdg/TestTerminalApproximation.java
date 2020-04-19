package sdg;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.junit.runner.RunWith;
import java.util.Arrays;
import sdg.equations.*;


@RunWith(Parameterized.class)
public class TestTerminalApproximation {

    @Parameterized.Parameters()
    public static Iterable<Object> data() {
        return Arrays.asList( new Object[] {
                new ConstantLinear(-1, 1, 0, 0, 3),
                new ConstantLinear(1, 1, 1, 0, 1),
                new NonLinear1(0, 0, 1),
                new NonLinear2(0.5, 0, 1),
                new NonLinear3(1, 0, 1),
                new NonLinear4(0.1, 0.5, 1, 0, 3),
                new NonLinear5(1, 0, Math.PI),
                new Linear1(0, 0, 6)
        });
    };

    private Equation equation;

    public TestTerminalApproximation(Equation equation) {
        this.equation = equation;
    }

    @Test
    public void test() {
        double[] approxAndSolution = terminalApprox(this.equation);
        assertEquals(approxAndSolution[0], approxAndSolution[1], 10e-5);
    }

    private double[] terminalApprox(Equation equation) {
        // Generate a mesh with 100 elements on the time interval [t0, T].
        int N = 100;
        Mesh mesh = new Mesh(equation.t0, equation.tN, N);
        Noise noise = new Noise(mesh);

        // We will use SDG method to approximate with a polynomial degree of p = 2.
        int p = 2;
        SDG sdg = new SDG();
        ApproxGlobal approxGlobal = sdg.Solve(equation, noise, mesh, p);
        ApproxLocal finalElementApprox = approxGlobal.localApproximations[N-1];

        // Get the approximation at time = T (terminal node in the last local approx).
        double approxT = finalElementApprox.terminal();

        // Compute the exact solution at time T.
        double totalNoise = noise.sum();
        double exactT = equation.exactSolution(equation.tN, totalNoise);

        return new double[] {approxT, exactT};
    }
}
