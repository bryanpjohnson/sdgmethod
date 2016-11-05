package sdg;

/**
 *
 * @author bryan.johnson
 */
public class Element {
    
    public double lowerEndpoint;
    public double upperEndpoint;
    public double length;
    
    public Element(double lowerEndpoint, double upperEndpoint)
    {
        this.lowerEndpoint = lowerEndpoint;
        this.upperEndpoint = upperEndpoint;
        this.length = upperEndpoint - lowerEndpoint;
    }
    
    public Element()
    {
        this.lowerEndpoint = 0;
        this.upperEndpoint = 0;
        this.length = 0;
    }
}