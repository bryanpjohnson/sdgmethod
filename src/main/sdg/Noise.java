package sdg;

import java.util.Random;

/**
 * Class for simulating 1-dimensional Brownian on a one-dimensional partitioned interval.
 */
public class Noise {
    
    public double[] discreteNoise;

    /**
     * Constructor for Noise.
     * @param mesh Instance of the sgd.Mesh class.
     */
    public Noise(Mesh mesh)
    {
        this.discreteNoise = generateNoise(mesh);
    }

    /**
     * Gets the total sum of all Brownian motion on the mesh (both negative and positive).
     * @return Double - the sum of all Brownian motion on the mesh.
     */
    public double sum()
    {
        double total = 0;

        for (int i = 0; i < discreteNoise.length; i++)
        {
                total += discreteNoise[i];
        }

        return total;
    }

    /**
     * Aggregates the noise into fewer bins.
     *
     * For example, suppose we have a mesh of size 4 with noise (W) on each elements. If we wish to approximate
     * this domain with only 2 elements, we can collapse the noise by summing across the original elements.
     *      original noise = [W0, W1, W2, W3]
     *      binned noise = [W0+W1, W2+W3]
     *
     * This will be used for simulation purposes if we want to change the partitioning size.
     *
     * @param segments Number of new segments
     */
    public void binNoise(int segments)
    {
        // Make sure that simulated noise length is evenly divisible by the requested number of bins.
        if (this.discreteNoise.length % segments != 0) {
            throw new IllegalArgumentException(
                    "The original simulated noise of length " + this.discreteNoise.length +
                            " is not evenly divisible by the requested number of bins: " + segments);
        }

        double[] binnedNoise = new double[segments];
	    int step = discreteNoise.length / segments;

        for (int i = 0; i < segments; i++)
        {
            double value = 0;

            for (int j = i*step; j < (i + 1)*step; ++j)
            {
                value += discreteNoise[j];
            }

            binnedNoise[i] = value;
        }

        this.discreteNoise = binnedNoise;
    }

    /**
     * Generates Gaussian random noise (Brownian motion) on the defined mesh.
     *
     * @param mesh Instance of the main.sdg.Mesh class.
     * @return Double array with the total noise on each mesh element.
     */
    private double[] generateNoise(Mesh mesh)
    {
        double[] noise = new double[mesh.numberElements];
        Random random = new Random();
        
        for(int k = 0; k < mesh.numberElements; k++)
        {
            Element element = mesh.elements[k];
            noise[k] = Math.sqrt(element.length)*random.nextGaussian();
        }
        
        return noise;
    }
}
