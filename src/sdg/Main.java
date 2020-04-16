package sdg;
import equations.*;
import solutions.*;


public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Equation we are approximating is an implementation of an abstract SDE class
        Equation1 equation = new Equation1();
        Solution1 solution = new Solution1();

        // Generate a mesh with 100 elements on the time interval [t0, T]
        Mesh mesh = new Mesh(equation.t0, equation.tN, 100);

        // Simulate Brownian motion on the interval [t0, T] with 100 discrete points
        Noise noise = new Noise(mesh);

        // We will approximate the SDE with N = 10 elements. Clone the more granular noise and aggregate
        // it into 10 buckets. This will be used for W(t3) - W(t2) for example.
        int N = 10;
        Mesh segmentedMesh = new Mesh(equation.t0, equation.tN, N);
        Noise segmentedNoise = new Noise(segmentedMesh);
        segmentedNoise.discreteNoise = noise.discreteNoise.clone();
        segmentedNoise.segmentNoise(N);

        // We will use SDG method to approximate with a polynomial degree of p = 2
        int p = 2;
        SDG sdg = new SDG();
        ApproxGlobal approxGlobal = sdg.Solve(equation, segmentedNoise, segmentedMesh, p);
        ApproxLocal finalElementApprox = approxGlobal.localApproximations[N-1];

        // Get the approximation at time = T (terminal node in the last local approx).
        double approxT = finalElementApprox.terminal();

        // Compute the exact solution at time T
        double totalNoise = noise.sum();
        double exactT = solution.terminal(equation, totalNoise);

        // Display the results
        System.out.println("Exact solution: " + exactT);
        System.out.println("Approximate solution: " + approxT);
    }
}
