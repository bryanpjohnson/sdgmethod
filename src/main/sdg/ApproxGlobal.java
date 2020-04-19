package sdg;

/**
 * This class represents the global approximation of the SDE across the specified mesh.
 * The global approximation is a collection of local approximations (one for each each element in the mesh).
 */
public class ApproxGlobal {
    
    ApproxLocal[] localApproximations;

    /**
     * Constructor for ApproxGlobal.
     * @param mesh Instance of the main.sdg.Mesh class.
     */
    public ApproxGlobal(Mesh mesh)
    {
        this.localApproximations = new ApproxLocal[mesh.numberElements];
    }
}
