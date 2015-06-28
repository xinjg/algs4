public class Subset {
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        int cnt = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {
            String value = StdIn.readString();
            queue.enqueue(value);
        }
        while (cnt > 0) {
            StdOut.println(queue.dequeue());
            cnt--;
        }
    }
}