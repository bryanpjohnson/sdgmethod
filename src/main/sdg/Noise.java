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
        Random random = new Random();
        this.discreteNoise = generateNoise(mesh, random);
    }

    /**
     * Constructor for Noise with fixed random seed.
     * @param mesh Instance of the sgd.Mesh class.
     */
    public Noise(Mesh mesh, long randomSeed)
    {
        Random random = new Random(randomSeed);
        this.discreteNoise = generateNoise(mesh, random);
    }

    /**
     * Sums the noise up until the i^th element.
     * @param stopIndex The i^th element to sum to.
     * @return double -- cumulative noise.
     */
    public double sumUntil(int stopIndex)
    {
        double total = 0;

        for (int i = 0; i <= stopIndex; i++)
        {
            total += this.discreteNoise[i];
        }

        return total;
    }

    /**
     * Gets the total sum of all Brownian motion on the mesh (both negative and positive).
     * @return Double - the sum of all Brownian motion on the mesh.
     */
    public double sum()
    {
        return sumUntil(this.discreteNoise.length - 1);
    }

    /**
     * Generates Gaussian random noise (Brownian motion) on the defined mesh.
     *
     * @param mesh Instance of the main.sdg.Mesh class.
     * @param random Instance of java.util.Random class (instantiate with fixed seed if you want determinism).
     * @return Double array with the total noise on each mesh element.
     */
    private double[] generateNoise(Mesh mesh, Random random)
    {
        double[] noise = new double[mesh.numberElements];
        
        for(int k = 0; k < mesh.numberElements; k++)
        {
            Element element = mesh.elements[k];
            noise[k] = Math.sqrt(element.length)*random.nextGaussian();
        }
        
        return noise;
    }
}
