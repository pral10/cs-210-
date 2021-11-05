import stdlib.StdOut;

public class Sample {
    // Entry point.
    public static void main(String[] args) {
        int lo = Integer.parseInt(args[0]); // lo(int) as command line argument
        int hi = Integer.parseInt(args[1]);  // hi(int) as command line argument
        int k = Integer.parseInt(args[2]); //  // k(int) as command line argument
        String mode = (args[3]); // mode(string) as command line argument

        ResizingArrayRandomQueue<Integer> q = new ResizingArrayRandomQueue<Integer>();
        if (!mode.equals("+") && !mode.equals("-")) {
            throw new IllegalArgumentException("Illegal mode");
        }

        for (int i = lo; i <= hi; i++) { // creating the random queue containing [lo,hi]
            q.enqueue(i);
        }
        if (mode.equals("+")) { // If mode is “+” (sampling with replacement), sample and write
            // k integers from q to standard output
            for (int i = 0; i < k; i++) {
                StdOut.println(q.sample());
            }
        } else {
            // If mode is “-” (sampling without replacement), dequeue and write k integers from q to
            // standard output
            for (int i = 0; i < k; i++) {
                StdOut.println(q.dequeue());
            }
        }
    }

}


