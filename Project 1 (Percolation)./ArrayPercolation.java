import stdlib.In;
import stdlib.StdOut;

// An implementation of the Percolation API using a 2D array.

public class ArrayPercolation implements Percolation {
    // percolation system with size n
    private int n;
    // instance variable boolean[] [] open
    private boolean[][] open;
    // Number of open sites
    private int openSites;


    // Constructs an n x n percolation system, with all sites blocked.
    public ArrayPercolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Illegal n");
        }
        this.n = n;
        this.open = new boolean[n][n];
        // initializing open sites to zero.
        this.openSites = 0;
        // Initializing everything in array to False = Blocked
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.open[i][j] = false;
            }
        }
    }


    // Opens site (i, j) if it is not already open.
    public void open(int i, int j) {
        if (i < 0 || i > n - 1 || j < 0 || j > n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        if (!open[i][j]) {
            this.open[i][j] = true;
            this.openSites++;
        }
    }


    // Returns true if site (i, j) is open, and false otherwise.
    public boolean isOpen(int i, int j) {
        if (i < 0 || i > n - 1 || j < 0 || j > n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        return this.open[i][j];
    }

    // Returns boolean value isFull
    public boolean isFull(int i, int j) {
        if (i < 0 || i > n - 1 || j < 0 || j > n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        // create an n * n array of booleans called full
        boolean[][] full = new boolean[this.n][this.n];
        for (int k = 0; k <= this.n; k++) {
            floodFill(full, 0, k);
        }
        return full[i][j];
    }

    // Returns the number of open sites.
    public int numberOfOpenSites() {
        return openSites;
    }


    // Returns true if this system percolates, and false otherwise.
    public boolean percolates() {

        for (int i = 0; i < this.n; i++) {
            if (isFull(this.n - 1, i)) {
                return true;
            }
        }
        return false;
    }

    // floodFill method for filling the open sites
    private void floodFill(boolean[][] full, int i, int j) {
        int n = full.length;
        if (i < 0 || i >= n || j < 0 || j >= n || !isOpen(i, j) || full[i][j]) {
            return;
        }

        full[i][j] = true;
        floodFill(full, i + 1, j);
        floodFill(full, i, j + 1);
        floodFill(full, i, j - 1);
        floodFill(full, i - 1, j);
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        ArrayPercolation perc = new ArrayPercolation(n);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
        StdOut.printf("%d x %d system:\n", n, n);
        StdOut.printf("  Open sites = %d\n", perc.numberOfOpenSites());
        StdOut.printf("  Percolates = %b\n", perc.percolates());
        if (args.length == 3) {
            int i = Integer.parseInt(args[1]);
            int j = Integer.parseInt(args[2]);
            StdOut.printf("  isFull(%d, %d) = %b\n", i, j, perc.isFull(i, j));
        }
    }
}