package sdg;

/**
 *
 * @author bryan.johnson
 */
public class Mesh {
    
    public double lowerEndpoint;
    public double upperEndpoint;
    public int numberElements;
    public double[] nodes;
    public Element[] elements;
    
    /*
	Constructor when a uniform mesh is desired.
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