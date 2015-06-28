import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A double-ended queue or deque (pronounced "deck") is a generalization of a
 * stack and a queue that supports adding and removing items from either the
 * front or the back of the data structure
 * 
 * @author xinjg
 * @param <Item>
 */
public class Deque<Item> implements Iterable<Item> {

    private Node head;
    private Node tail;
    private int size;

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return this.size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        checkAdd(item);
        // create new node and move head
        Node node = new Node();
        node.setValue(item);
        node.setNext(head);
        if (head != null)
            head.setPrev(node);
        head = node;
        if (this.size == 0)
            tail = head;
        this.size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        checkAdd(item);
        // create new node and move tail;
        Node node = new Node();
        node.setValue(item);
        if (tail != null)
            tail.setNext(node);
        node.setPrev(tail);
        tail = node;
        if (this.size == 0)
            head = tail;
        this.size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        // throw NoSuchElementException if deque is empty
        checkRemove();
        Node node = head;
        Item item = node.getValue();
        head = node.getNext();
        if (head != null)
            head.setPrev(null);
        else
            tail = null;
        node = null;
        this.size--;
        return item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        // throw NoSuchElementException if deque is empty
        checkRemove();
        Node node = tail;
        Item item = node.getValue();
        tail = node.getPrev();
        node = null;
        if (tail != null)
            tail.setNext(null);
        else {
            head = null;
        }
        this.size--;
        return item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        class DequeIterator implements Iterator<Item> {
            private Node node = head;

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean hasNext() {
                return node != null;
            }

            @Override
            public Item next() {
                if (node == null)
                    throw new NoSuchElementException();
                Item item = node.getValue();
                node = node.getNext();
                return item;
            }
        }
        return new DequeIterator();
    }

    private void checkRemove() {
        if (this.size == 0) {
            throw new NoSuchElementException("deque is empty");
        }
    }

    private void checkAdd(Item item) {
        if (item == null)
            throw new NullPointerException("cannot add null value");
    }

    private class Node {
        private Item value;
        private Node next;
        private Node prev;
        public Item getValue() {
            return value;
        }

        public void setValue(Item val) {
            this.value = val;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node nxt) {
            this.next = nxt;
        }

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prv) {
            this.prev = prv;
        }

    }

    // unit testing
    public static void main(String[] args) {
    }
}