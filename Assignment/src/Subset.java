public class Subset {
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        int cnt = Integer.parseInt(args[0]);
        In in = new In();
        while (!in.isEmpty()) {
            String value = in.readString();
            queue.enqueue(value);
        }
        while (cnt > 0) {
            StdOut.println(queue.dequeue());
            cnt--;
        }
    }
}