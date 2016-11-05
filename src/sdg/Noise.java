package sdg;

import java.util.Random;

/**
 *
 * @author bryan.johnson
 */
public class Noise {
    
    public double[] discreteNoise;
    
    public Noise(Mesh mesh)
    {
        this.discreteNoise = generateNoise(mesh);
    }
    
    public double sum()
    {
	double total = 0;

	for (int i = 0; i < discreteNoise.length; i++)
	{
            total += discreteNoise[i];
	}

	return total;
    }
    
    //FIX ME
    public void segmentNoise(int segments)
    {
        double[] segmentedNoise = new double[segments];
	int step = (int) discreteNoise.length / segments;

        for (int i = 0; i < segments; i++)
        {
            double value = 0;

            for (int j = i*step; j < (i + 1)*step; ++j)
            {
                value += discreteNoise[j];
            }

            segmentedNoise[i] = value;
        }

        this.discreteNoise = segmentedNoise;
    }
    
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