import dsa.WeightedQuickUnionUF;
import stdlib.In;
import stdlib.StdOut;

// An implementation of the Percolation API using the UF data structure.
public class UFPercolation implements Percolation {
    private  int n; // instance variable n
    private  boolean[][] open; // instance variable boleean [][] open
    private  WeightedQuickUnionUF uf; // Union-find representation of the percolation system,
    // WeightedQuickUnionUF uf
    private int openSites; // number of open sites
    private WeightedQuickUnionUF ufFul; // object for back wash.

    // Constructs an n x n percolation system, with all sites blocked.
    public UFPercolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Illegal n");
        }
        // initialize instance variable
        this.n = n;
        openSites = 0;
        // construct an n *n percolation system with all sites blocked.
        this.open = new boolean[n][n];
        uf  = new WeightedQuickUnionUF(n * n + 2);
        // create the union find for backwash fix.
        ufFul = new WeightedQuickUnionUF(n * n +2);
    }




    // Opens site (i, j) if it is not already open.
    public void open(int i, int j) {
        if (i < 0 || i >= n || j < 0 || j >= n) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        // if site (i, j) is not open open the site.
        if (!open[i][j]) {
            open[i][j] = true;
            // increment the openSites by one
            openSites++;
            // if the site in the first or last row, connect the corresponding uf
            // site with the source(or sink)
            int s1 = 0;
            int s2 = encode(s1, j);
            if (i == 0) {
                uf.union(s1, s2);
                ufFul.union(s1, s2);
            }
            // if the site in the last row is sink
            if (i == n - 1) {
                int site1 = n - 1;
                int site2 = encode(site1, j);
                uf.union(n * n + 1, site2);
            }
            // if any of the neighbor o north, east , west and south of site (i, j) is open,
            // connect uf site corresponding to site (i, j) with the uf site corresponding
            // neighbor site.
            if (i > 0 && isOpen(i - 1, j)) {
                int site1 = encode(i, j);
                int site2 = encode(i - 1, j);
                uf.union(site1, site2);
                ufFul.union(site1, site2);
            }
            if (i < (n - 1) && isOpen(i + 1, j)) {
                int site1 = encode(i, j);
                int site2 = encode(i + 1, j);
                uf.union(site1, site2);
                ufFul.union(site1, site2);
            }
            if (j > 0 && isOpen(i, j - 1)) {
                int site1 = encode(i, j);
                int site2 = encode(i, j - 1);
                uf.union(site1, site2);
                ufFul.union(site1, site2);
            }
            if (j < (n - 1) && isOpen(i, j + 1)) {
                int site1 = encode(i, j);
                int site2 = encode(i, j + 1);
                uf.union(site1, site2);
                ufFul.union(site1, site2);
            }
        }
    }

    // Returns true if site (i, j) is open, and false otherwise.
    public boolean isOpen(int i, int j) {
        if (i < 0 || i >= n || j < 0 || j >= n) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }

        return open[i][j];
    }

    // Returns true if site (i, j) is full, and false otherwise.
    public boolean isFull(int i, int j) {
        if (i < 0 || i >= n || j < 0 || j >= n) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        // return whether site (i, j) is full or not.
        int x1 = i;
        int x2 = encode(x1, j);
        return isOpen(x1, j) && ufFul.connected(0, x2);
    }

    // Returns the number of open sites.
    public int numberOfOpenSites() {
        return openSites;
    }


    // Return whether the system percolates or not - a system percolates if sink is
    // connected to source.
    public boolean percolates() {
        return uf.connected(0, n * n + 1);
    }


    // Returns an integer ID (1...n) for site (i, j).
    private int encode(int i, int j) {
        return (i * n) + j + 1;

    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        UFPercolation perc = new UFPercolation(n);
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