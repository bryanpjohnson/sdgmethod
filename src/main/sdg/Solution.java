package sdg;

/**
 * This abstract class is used to hold the exact solution of an SDE (when known) for testing purposes.
 */
public abstract class Solution {
    public abstract double terminal(Equation equation, double totalNoise);
}