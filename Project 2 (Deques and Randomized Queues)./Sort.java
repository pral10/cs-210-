import dsa.LinkedStack;
import stdlib.StdIn;
import stdlib.StdOut;

public class Sort {
    // Entry point.
    public static void main(String[] args) {
        LinkedDeque<String> d = new LinkedDeque<>(); //  creating a deque d.
        // for each word w read from standard input
        while (!StdIn.isEmpty()) {
            String w = StdIn.readString();
            // add w to front of 'd' if d is empty
            if (d.isEmpty()) {
                d.addFirst(w);
            } else if (less(w, d.peekFirst())) {
                d.addFirst(w);
            } else if (less(d.peekLast(), w)) {
                d.addLast(w);
            } else {
                // create stack named 's' to store the elements from the first front of d
                LinkedStack<String> s = new LinkedStack<>();
                // when the front word of 's'  is less than W.
                while (less(d.peekFirst(), w)) {
                    // removes  from deque d and add to stacks
                    s.push(d.removeFirst());
                }
                // adds w to front of d.
                d.addFirst(w);
                while (!s.isEmpty()) {
                    // removes all the element from  stack and add to deque d.
                    d.addFirst(s.pop());
                }
            }
        }
        while (!d.isEmpty()) {
            StdOut.println(d.peekFirst());
            d.removeFirst();
        }

    }
    // Returns true if v is less than w according to their lexicographic order, and false otherwise.
    private static boolean less(String v, String w) {
        return v.compareTo(w) < 0;
    }
}
