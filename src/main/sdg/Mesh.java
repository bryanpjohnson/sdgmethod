package sdg;

/**
 * Represents the one-dimensional geometric partitioning of the domain we are solving the SDE on.
 * Each mesh contains non-overlapping elements with varying lengths
 */
public class Mesh {
    
    public double lowerEndpoint;
    public double upperEndpoint;
    public int numberElements;
    public double[] nodes;
    public Element[] elements;

    /**
     * Constructor for Mesh if you want a uniform partitioning of your domain (every element has equal length).
     *
     * @param lowerEndpoint Starting point of a mesh defined on [t0, tN], i.e., t0.
     * @param upperEndpoint Ending point of a mesh defined on [t0, tN], i.e., tN.
     * @param numberElements The number of partitions/elements you want on the mesh. These will
     *                       have length (tN-t0)/N.
     */
    public Mesh(double lowerEndpoint, double upperEndpoint, int numberElements)
    {
        this.lowerEndpoint = lowerEndpoint;
        this.upperEndpoint = upperEndpoint;
        this.numberElements = numberElements;
        
        double[] nodes = generateNodes(lowerEndpoint, upperEndpoint, numberElements);
        this.nodes = nodes;
        
        this.elements = makeElements(nodes);
    }

    /**
     * Generates starting points for the elements on a uniform partitioning on the domain [t0, tN]
     * (every element equal length). [t0, t1, t2, t3, ... , tN-1]
     *
     * @param lowerEndpoint Starting point of a mesh defined on [t0, tN], i.e., t0.
     * @param upperEndpoint Ending point of a mesh defined on [t0, tN], i.e., tN.
     * @param numberElements The number of partitions/elements you want on the mesh. These will
     *                       have length (tN-t0)/N.
     *
     * @return Array of doubles containing the starting point of each partition.
     */
    private double[] generateNodes(double lowerEndpoint, double upperEndpoint, int numberElements)
    {
        double h = (upperEndpoint - lowerEndpoint)/numberElements;    
        double[] nodes = new double[numberElements+1];
    
        for (int i = 0; i < nodes.length; i++)
        {
            nodes[i] = lowerEndpoint + i*h;
        }
        
        return nodes;
    }

    /**
     * Constructs Element classes out of a list of node points (lower bounds of each element on the partition).
     *
     * @param nodes Lower bounds of elements, [t0, t1, t2, t3, ... , tN-1].
     * @return Array of Element classes.
     */
    private Element[] makeElements(double[] nodes)
    {
        Element[] elements = new Element[nodes.length-1];
        
        for (int i = 0; i < nodes.length-1; i++)
        {
            Element element = new Element(nodes[i], nodes[i+1]);
            elements[i] = element;
        }
        
        return elements;
    }   
}