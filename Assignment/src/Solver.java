import java.util.Iterator;

public class Solver {
    private class Pripority implements Comparable<Pripority> {
        private int pripority;
        private Board board;
        Board board(){
            return this.board;
        }
        Pripority(Board board, int moves) {
            this.board = board;
            this.pripority = board.hamming() + moves;
        }

        @Override
        public int compareTo(Pripority that) {
            if (this.pripority > that.pripority) {
                return 1;
            } else if (this.pripority < that.pripority) {
                return -1;
            } else
                return 0;
        }
    }

    private MinPQ<Pripority> minPQ = new MinPQ<Pripority>();
    private MinPQ<Pripority> twinMinPQ = new MinPQ<Pripority>();
    private int moves = 0;
    private Board prev = null;
    private Board twinPrev = null;
    private Queue<Board> queue = new Queue<Board>();
    private boolean isSolvable = false;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException("initial board cannot be null");
        }
        minPQ.insert(new Pripority(initial, 0));
        Board twin = initial.twin();
        twinMinPQ.insert(new Pripority(twin, 0));
        queue.enqueue(initial);
        while (!minPQ.min().board().isGoal() && !twinMinPQ.min().board().isGoal()) {
            Board min = minPQ.delMin().board();
            Board twinMin = twinMinPQ.delMin().board();
            while (!minPQ.isEmpty()) {
//                type type = (type) en.nextElement();
                minPQ.delMin();
            }
            while (!twinMinPQ.isEmpty()) {
                twinMinPQ.delMin();
            }
            this.moves++;
//            queue.enqueue(min);
            for (Iterator<Board> iterator = min.neighbors().iterator(); iterator
                    .hasNext();) {
                Board neighbor = iterator.next();
                if (!neighbor.equals(prev)) {
                    minPQ.insert(new Pripority(neighbor, moves));
                }
            }
            if (prev!=null) {
                queue.enqueue(min);
            }
            prev = min;
            for (Iterator<Board> iterator = twinMin.neighbors().iterator(); iterator
                    .hasNext();) {
                Board neighbor = iterator.next();
                if (!neighbor.equals(twinPrev)) {
                    twinMinPQ.insert(new Pripority(neighbor, moves));
                }
            }
            twinPrev = twinMin;
        }
        if (minPQ.min().board().isGoal()) {
            this.isSolvable = true;
        } else {
            this.isSolvable = false;
            this.moves = -1;
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return this.isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return this.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (this.isSolvable)
            return this.queue;
        return null;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
