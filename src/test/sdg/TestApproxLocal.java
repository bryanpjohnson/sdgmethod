package sdg;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class TestApproxLocal {
    @Test
    public void testTerminal() {
        Element e = new Element(0.5, 1);
        ApproxLocal a = new ApproxLocal(e, 3);
        a.coefficients.set(0, 0, 0.35);
        a.coefficients.set(1, 0, 0.55);
        a.coefficients.set(2, 0, 0.80);

        double expected = 0.35 + 0.55 + 0.80;
        double actual = a.terminal();
        assertEquals(expected, actual, 10e-5);
    }
}
