import java.util.ArrayList;
import java.util.List;

public class Board {
    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    private int[][] blocks;
    private int dimension;
    private int hamming;
    private int manhattan;
    private int blankRow, blankCol;
    private boolean isGoal = false;

    public Board(int[][] blocks) {

        this.dimension = blocks.length;
        this.blocks = copy(blocks);
        blankRow = dimension - 1;
        blankCol = dimension - 1;
        /* hamming manhattan */
        for (int i = 0; i < this.blocks.length; i++) {
            for (int j = 0; j < this.blocks[i].length; j++) {
                // this.blocks[i][j]=blocks[i][j];//copy blocks
                /*
                 * do not count the blank square when computing the Hamming or
                 * Manhattan priorities
                 */
                int block = this.blocks[i][j];
                if (block != 0) {
                    // if (!(i == dimension - 1 && j == dimension - 1)) {
                    int rightValue = i * dimension + j + 1;
                    if (this.blocks[i][j] != rightValue) {
                        hamming += 1;
                    }
                    int times = block / dimension;
                    int mod = block % dimension;
                    int rightRow, rightCol;
                    rightCol = mod - 1;
                    rightRow = times;
                    if (times > 0 && mod == 0) {
                        rightCol = dimension - 1;
                        rightRow--;
                    }
                    manhattan += Math.abs(rightRow - i)
                            + Math.abs(rightCol - j);
                } else {
                    blankCol = j;
                    blankRow = i;
                }
            }
        }
        this.isGoal = hamming == 0;
    }

    // board dimension N
    public int dimension() {
        return this.dimension;
    }

    // number of blocks out of place
    public int hamming() {
        return this.hamming;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return this.manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return this.isGoal;
    }

    // a board that is obtained by exchanging two adjacent blocks in the same
    // row
    public Board twin() {
        int[][] twinBlocks = copy(this.blocks);
        for (int i = 0; i < twinBlocks.length; i++) {
            if (this.blankRow != i) {
                // swap blocks[i][0] with blocks[i][1]
                int tmp = twinBlocks[i][0];
                twinBlocks[i][0] = twinBlocks[i][1];
                twinBlocks[i][1] = tmp;
                break;
            }
        }
        return new Board(twinBlocks);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null)
            return false;
        if (this.getClass() != y.getClass())
            return false;
        Board that = (Board) y;
        if (this.dimension != that.dimension)
            return false;
        if (this.hamming != that.hamming)
            return false;
        if (this.manhattan != that.manhattan)
            return false;
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                if (this.blocks[i][j] != that.blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /*
     * all neighboring boards(those that can be reached in one move from the
     * dequeued search node)
     */
    public Iterable<Board> neighbors() {
        int max = dimension - 1;
        List<Board> neighbors = new ArrayList<Board>(4);
        // move blank square up
        if (blankRow > 0) {
            int[][] blocksUp = copy(this.blocks);
            move(blocksUp, blankRow - 1, blankCol, blankRow, blankCol);
            neighbors.add(new Board(blocksUp));
        }
        // move blank square down
        if (blankRow < max) {
            int[][] blocksDown = copy(this.blocks);
            move(blocksDown, blankRow + 1, blankCol, blankRow, blankCol);
            neighbors.add(new Board(blocksDown));
        }
        // move blank square left
        if (blankCol > 0) {
            int[][] blocksLeft = copy(this.blocks);
            move(blocksLeft, blankRow, blankCol - 1, blankRow, blankCol);
            neighbors.add(new Board(blocksLeft));
        }
        // move blank square right
        if (blankCol < max) {
            int[][] blocksRight = copy(this.blocks);
            move(blocksRight, blankRow, blankCol + 1, blankRow, blankCol);
            neighbors.add(new Board(blocksRight));
        }
        return neighbors;
    }

    private void move(int[][] moveBlocks, int row, int col, int nRow, int nCol) {
        moveBlocks[nRow][nCol] = moveBlocks[row][col];
        moveBlocks[row][col] = 0;
    }

    // string representation of this board (in the output format specified
    // below)
    public String toString() {
        StringBuilder sb = new StringBuilder(this.dimension + "\n");
        // StdOut.println(this.dimension);
        for (int i = 0; i < this.dimension; i++) {
            sb.append(" ");
            for (int j = 0; j < this.dimension; j++) {
                sb.append(blocks[i][j]).append("  ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        StdOut.println("initial board:");
        StdOut.println(initial);
        StdOut.println("neighbors:");
        for (Board neighbor : initial.neighbors()) {
            StdOut.println(neighbor.toString());
        }
    }

    private int[][] copy(int[][] copyBlocks) {
        int length = copyBlocks.length;
        int[][] copy = new int[length][length];
        for (int i = 0; i < copy.length; i++) {
            for (int j = 0; j < copy[i].length; j++) {
                copy[i][j] = copyBlocks[i][j];
            }
        }
        return copy;
    }

}