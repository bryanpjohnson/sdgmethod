package sdg;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class TestLegendre {
    @Parameterized.Parameters()
    public static Iterable<Object[]> data() {
        return Arrays.asList( new Object[][] {
                {0, -1.0, 1.0},
                {0, -0.5, 1.0},
                {0, 0.0, 1.0},
                {0, 0.5, 1.0},
                {0, 1.0, 1.0},
                {1, -1.0, -1.0},
                {1, -0.5, -0.5},
                {1, 0.0, 0.0},
                {1, 0.5, 0.5},
                {1, 1.0, 1.0},
                {2, -1.0, 1.0},
                {2, -0.5, -0.125},
                {2, 0.0, -0.5},
                {2, 0.5, -0.125},
                {2, 1.0, 1.0},
                {3, -1.0, -1.0},
                {3, -0.5, 0.4375},
                {3, 0.0, 0.0},
                {3, 0.5, -0.4375},
                {3, 1.0, 1.0},
                {4, -1.0, 1.0},
                {4, -0.5, -0.2890625},
                {4, 0.0, 0.375},
                {4, 0.5, -0.2890625},
                {4, 1.0, 1.0}
        });
    };

    private int degree;
    private double x;
    private double expected;

    public TestLegendre(int degree, double x, double expected) {
        this.degree = degree;
        this.x = x;
        this.expected = expected;
    }

    @Test
    public void test() {
        Legendre l = new Legendre();
        double actual = l.basis(this.degree, this.x);
        assertEquals(this.expected, actual, 10e-5);
    }
}
