package sdg;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;


public class TestMesh {
    @Test
    public void testMesh() {
        Mesh m = new Mesh(0.0, 1.0, 5);
        double[] expected = new double[] {0.0, 0.2, 0.4, 0.6, 0.8, 1.0};
        assertArrayEquals(expected, m.nodes, 10e-15);
    }
}
