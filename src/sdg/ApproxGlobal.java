package sdg;

public class ApproxGlobal {
    
    ApproxLocal[] localApproximations;
    double terminalValue;
    
    public ApproxGlobal(Mesh mesh)
    {
        this.localApproximations = new ApproxLocal[mesh.numberElements];
    }
}
