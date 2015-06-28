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
        node.value = item;
        node.next = head;
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
        node.value = item;
        if (tail != null)
            tail.next = node;
        node.prev = tail;
        tail = node;
        this.size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        // throw NoSuchElementException if deque is empty
        checkRemove();
        Node node = head;
        Item item = node.value;
        head = node.next;
        if (head != null)
            head.prev = null;
        node = null;
        this.size--;
        return item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        // throw NoSuchElementException if deque is empty
        checkRemove();
        Node node = tail;
        Item item = node.value;
        tail = node.prev;
        node = null;
        if (tail != null)
            tail.next = null;
        this.size--;
        return item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            Node node = head;

            @Override
            public boolean hasNext() {
                return node != null;
            }

            @Override
            public Item next() {
                if (node == null)
                    throw new NoSuchElementException();
                Item item = node.value;
                node = node.next;
                return item;
            }
        };
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
        Item value;
        Node next;
        Node prev;
    }

    // unit testing
    public static void main(String[] args) {
    }
}