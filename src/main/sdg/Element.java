package sdg;

/**
 * Represents the geometry of a single element in our one-dimensional mesh.
 */
public class Element {
    
    public double lowerEndpoint;
    public double upperEndpoint;
    public double length;

    /**
     * Constructor for Element.
     * @param lowerEndpoint The starting point of the element. i.e., t for an element defined on [t, T].
     * @param upperEndpoint The ending point of the element, i.e., T for an element defined on [t, T].
     */
    public Element(double lowerEndpoint, double upperEndpoint)
    {
        this.lowerEndpoint = lowerEndpoint;
        this.upperEndpoint = upperEndpoint;
        this.length = upperEndpoint - lowerEndpoint;
    }
}