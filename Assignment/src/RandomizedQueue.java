/*
 * Corner cases. The order of two or more iterators to the same randomized queue must be mutually independent; 
 * each iterator must maintain its own random order. Throw a java.lang.NullPointerException if the client attempts to add a null item; 
 * throw a java.util.NoSuchElementException if the client attempts to sample or dequeue an item from an empty randomized queue;
 * throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator; 
 * throw a java.util.NoSuchElementException if the client calls the next() method in the iterator and there are no more items to return.
 * Performance requirements. 
 * Your randomized queue implementation must support each randomized queue operation (besides creating an iterator) in constant amortized time 
 * and use space proportional to the number of items currently in the queue. That is, any sequence of M randomized queue operations
 * (starting from an empty queue) should take at most cM steps in the worst case, for some constant c.
 * Additionally, your iterator implementation must support operations next() and hasNext() in constant worst-case time;
 * and construction in linear time; you may use a linear amount of extra memory per iterator.
 */
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Assignment : http://coursera.cs.princeton.edu/algs4/assignments/queues.html
 * 
 * @author xinjg
 * @param <Item>
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INIT_CAPACITY = 10;
    private Item[] array; // array to hold items
    private int size;
    private int next; // position to add a new item
    private java.util.Random random = new java.util.Random();

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.size = 0;
        this.next = 0;
//        random.setSeed(System.currentTimeMillis());
    }

    // is the queue empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // return the number of items on the queue
    public int size() {
        return this.size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException("cannot add null value");
        }
        if (array == null)
            array = (Item[]) new Object[INIT_CAPACITY];
        // resizing array
        if (next == array.length) {
            int capacity = 2 * this.size + INIT_CAPACITY;
            resizeArray(capacity);
        }
        array[this.next++] = item;
        this.size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (this.size == 0)
            throw new NoSuchElementException();
        if (this.size == this.array.length / 4 && this.array.length > 10) {
            resizeArray(this.size * 2);
        }
        int randomIndex = random.nextInt(next);
        while (array[randomIndex] == null) {
            randomIndex = random.nextInt(next);
        }
        Item retValue = array[randomIndex]; // set return value
        this.array[randomIndex] = null; // remove from queue
        this.size--;
        return retValue;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (this.size == 0)
            throw new NoSuchElementException();
        // java.util.Random random = new java.util.Random();
        int randomIndex = random.nextInt(next);
        while (array[randomIndex] == null) {
            randomIndex = random.nextInt(next);
        }
        return this.array[randomIndex];
    }

    private class RandomIterator implements Iterator<Item> {
        private int iterableSize;
        private Item[] itrrableholder;
        private int iterableNext;
        private java.util.Random itRandom = new java.util.Random();

        RandomIterator() {
            iterableSize = size;
            itrrableholder = (Item[]) new Object[iterableSize];
//            itRandom.setSeed(System.currentTimeMillis());
            // copy items
            int k = 0;
            for (int i = 0; i < next; i++) {
                if (array[i] != null) {
                    itrrableholder[k++] = array[i];
                }
            }
        }

        @Override
        public boolean hasNext() {
            return iterableSize != 0;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            iterableNext = itRandom.nextInt(size);
            while (itrrableholder[iterableNext] == null) {
                iterableNext = itRandom.nextInt(size);
            }
            Item ret = itrrableholder[iterableNext]; // set return value
            itrrableholder[iterableNext] = null;
            iterableSize--;
            return ret;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private void resizeArray(int capaticy) {
        Item[] holder = (Item[]) new Object[capaticy];
        int j = 0;
        for (int i = 0; i < this.array.length; i++) {
            if (this.array[i] != null) {
                holder[j++] = array[i];
            }
        }
        this.next = j;
        this.array = holder;
        holder = null;
    }

    // unit testing
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        queue.enqueue("aa");
        queue.enqueue("bb");
        queue.enqueue("cc");
        queue.enqueue("dd");
        queue.enqueue("ee");
        while (!queue.isEmpty())
            System.out.println(queue.dequeue());
    }

}