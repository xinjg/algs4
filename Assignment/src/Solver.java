import java.util.Iterator;

public class Solver {
    private class Pripority implements Comparable<Pripority> {
        private int moves;
        private int pripority;
        private Board board;

        Pripority(Board board, int moves) {
            this.board = board;
            int hanhattan = 0;
            if (board != null) {
                hanhattan = board.manhattan();
            }
            this.moves = moves;
            this.pripority = moves + hanhattan;
        }

        Board board() {
            return this.board;
        }

        int moves() {
            return this.moves;
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

    private int moves = 0;
    private Pripority prev = null;
    private Pripority twinPrev = null;
    private Queue<Board> queue = new Queue<Board>();
    private boolean isSolvable = false;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException("initial board cannot be null");
        }
        MinPQ<Pripority> minPQ = new MinPQ<Pripority>();
        MinPQ<Pripority> twinMinPQ = new MinPQ<Pripority>();
        minPQ.insert(new Pripority(initial, 0));
        Board twin = initial.twin();
        twinMinPQ.insert(new Pripority(twin, 0));
        prev = new Pripority(null, 0);
        twinPrev = new Pripority(null, 0);
        while (!minPQ.min().board().isGoal()
                && !twinMinPQ.min().board().isGoal()) {
            Pripority minPripority = minPQ.delMin();
            Board min = minPripority.board();
            queue.enqueue(min);
            Pripority twinMinPripoity = twinMinPQ.delMin();
            Board twinMin = twinMinPripoity.board();
            int step = minPripority.moves() + 1;
            for (Iterator<Board> iterator = min.neighbors().iterator(); iterator
                    .hasNext();) {
                Board neighbor = iterator.next();
                if (!neighbor.equals(prev.board())) {
                    minPQ.insert(new Pripority(neighbor, step));
                }
            }
            prev = minPripority;
            int twinStep = twinPrev.moves() + 1;
            for (Iterator<Board> iterator = twinMin.neighbors().iterator(); iterator
                    .hasNext();) {
                Board neighbor = iterator.next();
                if (!neighbor.equals(twinPrev.board())) {
                    twinMinPQ.insert(new Pripority(neighbor, twinStep));
                }
            }
            twinPrev = twinMinPripoity;
        }
        if (minPQ.min().board().isGoal()) {
            Pripority goalPripority=minPQ.delMin();
            Board goal=goalPripority.board();
            this.queue.enqueue(goal);
            this.isSolvable = true;
            this.moves = goalPripority.moves();
        } else {
            this.isSolvable = false;
            this.moves = -1;
        }
        minPQ=null;
        twinMinPQ=null;
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
