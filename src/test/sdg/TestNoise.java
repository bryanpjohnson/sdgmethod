package sdg;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;


public class TestNoise {
    @Test
    public void testNoiseRandom() {
        Mesh m = new Mesh(0.0, 1.0, 5);
        Noise n = new Noise(m);
        assertEquals(m.elements.length, n.discreteNoise.length,10e-15);
    }

    @Test
    public void testNoiseFixed() {
        Mesh m = new Mesh(0.0, 1.0, 5);
        Noise n = new Noise(m, 10);

        double[] expected = new double[] {
                0.3911682942770887,
                -0.4111432854977731,
                0.5066894927549782,
                -0.20564029945305531,
                0.32816938819140673
        };

        assertArrayEquals(expected, n.discreteNoise,10e-15);
    }

    @Test
    public void testNoiseSumUntil() {
        Mesh m = new Mesh(0.0, 1.0, 5);
        Noise n = new Noise(m, 10);

        double actual = n.sumUntil(2);
        double expected = 0.486714501534293;

        assertEquals(actual, expected,10e-15);
    }

    @Test
    public void testNoiseSum() {
        Mesh m = new Mesh(0.0, 1.0, 5);
        Noise n = new Noise(m, 10);

        double actual = n.sum();
        double expected = 0.609243590272644;

        assertEquals(actual, expected,10e-15);
    }
}