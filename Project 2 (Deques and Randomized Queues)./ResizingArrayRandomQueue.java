import java.util.Iterator;
import java.util.NoSuchElementException;

import stdlib.StdOut;
import stdlib.StdRandom;

// A data type to represent a random queue, implemented using a resizing array as the underlying
// data structure.
public class ResizingArrayRandomQueue<Item> implements Iterable<Item> {
    private int n = 0; // size of queue
    private Item[] q; // array to store items of queue


    // Constructs an empty random queue.
    public ResizingArrayRandomQueue() {
        q = (Item[]) new Object[2];
    }

    // Returns true if this queue is empty, and false otherwise.
    public boolean isEmpty() {
        return n == 0;
    }

    // Returns the number of items in this queue.
    public int size() {
        return n;
    }

    // Adds item to the end of this queue.
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException("item is null");
        }
        if (n == q.length) { // If q is at full capacity, resize it to twice its current capacity
            resize(2 * q.length);
        }
        q[n++] = item; // Insert the given item in q at index n and increment by 1.
    }

    // Returns a random item from this queue.
    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Random queue is empty");
        }
        return q[StdRandom.uniform(n)]; // return q[r] between [o,n)
    }

    // Removes and returns a random item from this queue.
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Random queue is empty");
        }
        int r = StdRandom.uniform(n); //  r as random integer between [0, n)
        Item item = q[r];
        // move the last item to fill in the place of removed item
        q[r] = q[n - 1];
        q[n - 1] = null;

        // shrink the array if necessary
        if (n > 0 && n == q.length / 4) {
            resize(q.length / 2);
        }
        n--;
        return item;
    }

    // Returns an independent iterator to iterate over the items in this queue in random order.
    public Iterator<Item> iterator() {
        return new RandomQueueIterator();
    }

    // Returns a string representation of this queue.
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Item item : this) {
            sb.append(item);
            sb.append(", ");
        }
        return n > 0 ? "[" + sb.substring(0, sb.length() - 2) + "]" : "[]";
    }

    // An iterator, doesn't implement remove() since it's optional.
    private class RandomQueueIterator implements Iterator<Item> {
        private int current; // index of current item in items.
        private Item[] items; // array to store the items of q on items

        // Constructs an iterator.
        public RandomQueueIterator() {
            items = (Item[]) new Object[n]; // creates item with capacity n

            for (int k = 0; k < n; k++) { // copy the n items from q to items.
                items[k] = q[k];

            }
            StdRandom.shuffle(items); // shuffle items
            current = 0; // set current to zero
        }

        // Returns true if there are more items to iterate, and false otherwise.
        public boolean hasNext() {
            return current < n;
        }

        // Returns the next item.
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("Iterator is exhausted");
            }
            // Return the item in items at index current and advance current by one
            return items[current++];
        }

        // Unsupported method.
        public void remove() {
            throw new UnsupportedOperationException("remove() is not supported");
        }
    }

    // Resizes the underlying array.
    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < n; i++) {
            if (q[i] != null) {
                temp[i] = q[i];
            }
        }
        q = temp;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        ResizingArrayRandomQueue<Integer> q = new ResizingArrayRandomQueue<Integer>();
        int sum = 0;
        for (int i = 0; i < 1000; i++) {
            int r = StdRandom.uniform(10000);
            q.enqueue(r);
            sum += r;
        }
        int iterSumQ = 0;
        for (int x : q) {
            iterSumQ += x;
        }
        int dequeSumQ = 0;
        while (q.size() > 0) {
            dequeSumQ += q.dequeue();
        }
        StdOut.println("sum       = " + sum);
        StdOut.println("iterSumQ  = " + iterSumQ);
        StdOut.println("dequeSumQ = " + dequeSumQ);
        StdOut.println("iterSumQ + dequeSumQ == 2 * sum? " + (iterSumQ + dequeSumQ == 2 * sum));
    }
}
