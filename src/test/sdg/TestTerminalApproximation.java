package sdg;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import sdg.equations.*;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class TestTerminalApproximation {

    @Parameterized.Parameters()
    public static Iterable<Object> data() {
        return Arrays.asList( new Object[] {
                new ConstantLinear(-1, 1, 0, 0, 3),
                new ConstantLinear(1, 2, 1, 0, 1),
                new ConstantLinear(3, 0, 1, 0, 1),
                new NonLinear1(1, 2, 100, 0, 1),
                new Linear1(0, 0, 6),
                new Linear2(0, 0, 1),
                new Linear3(1, 0, 1),
                new Linear4(0.1, 0.5, 1, 0, 3),
                new NonLinear2(0.5, 0, 1),
                new NonLinear3(1, 0, Math.PI),
                new NonLinear4(1.5, 0, 0, 1),
                new NonLinear5(1, 2, 0, 0, 1),
                new NonLinear6(0, 0, 1)
        });
    };

    private Equation equation;

    public TestTerminalApproximation(Equation equation) {
        this.equation = equation;
    }

    @Test
    public void test() {
        double[] exactAndApprox = terminalApprox(this.equation);
        assertEquals(exactAndApprox[0], exactAndApprox[1], 10e-5);
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

        return new double[] {exactT, approxT};
    }
}
