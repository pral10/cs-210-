import stdlib.StdOut;
import stdlib.StdRandom;
import stdlib.StdStats;

public class PercolationStats {
   // Instance Variables
    private int m;
    private double[] x; // instance array x[]


    // percolation method for m experiment
    public PercolationStats(int n, int m) {
        this.m = m;
        if (n <= 0 || this.m <= 0) {
            throw new IllegalArgumentException("Illegal n or m");
        }
        x = new double[this.m];

        for (int i = 0; i < this.m; i++) {
            // create an n*n percolation system ( use the UFPercolation implementation)
            UFPercolation per = new UFPercolation(n);
            // until the system percolates, choose a site (i, j) at random and open.
            while (!per.percolates()) {
                // choose a site (i, j) , randomly.
                int  rand1 = StdRandom.uniform(0, n);
                int rand2 = StdRandom.uniform(0, n);
                // open if it is not open.
                per.open(rand1, rand2);
            }
            // calculate percolation threshold as the fraction of sites opened
            // store the value in array x.
            int y = per.numberOfOpenSites();
            x[i] = y / (n * n * 1.0);
        }

    }

    // Returns sample mean of percolation threshold.
    public double mean() {
        // Return the mean of values in x[]
        return StdStats.mean(x);
    }
    // Returns sample standard deviation of  percolation threshold.
    public double stddev() {
        // Return the standard deviation of values in x[]
        return StdStats.stddev(x);
    }

    // returns low endpoint of the 95% confidence interval.
    public double confidenceLow() {
        double mn = this.mean();
        double result = mn - ((1.96 * (this.stddev()) / Math.sqrt(this.m)));
        return result;
    }

    // Returns high endpoint of the 95% confidence interval.
    public double confidenceHigh() {
        return this.mean() + ((1.96 * (this.stddev())/ Math.sqrt(this.m)));

    }



    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, m);
        StdOut.printf("Percolation threshold for a %d x %d system:\n", n, n);
        StdOut.printf("  Mean                = %.3f\n", stats.mean());
        StdOut.printf("  Standard deviation  = %.3f\n", stats.stddev());
        StdOut.printf("  Confidence interval = [%.3f, %.3f]\n", stats.confidenceLow(),
                stats.confidenceHigh());
    }
}