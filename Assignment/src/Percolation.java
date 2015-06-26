//http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
public class Percolation {

    private WeightedQuickUnionUF un;
    private int N;
    private int top, bottom;
    private boolean[] isOpen;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        if(N < 1){
            throw new IllegalArgumentException("N :" + N);
        }
        this.N = N;
        // N^2+1 is the victual top site,N^2+2 is the victual bottom site
        int cnt = N * N;
        isOpen = new boolean[cnt + 3];
        un = new WeightedQuickUnionUF(cnt + 3);
        this.top = cnt + 1; // N
        this.bottom = cnt + 2;
        isOpen[top] = true;
        isOpen[bottom] = true;
        for (int i = 1; i <= N; i++) {
            int first = site(1, i);
//            int last = site(N, i);
            un.union(this.top, first);
            // un.union(this.bottom, last);
        }
    }

    private void check(int i, int j) {
        if (i < 1 || i > N || j < 1 || j > N)
            throw new IndexOutOfBoundsException("Invalid input " + i + "-" + j
                    + "-" + N);
    }

    // site up to (row i, column j), return -1 if x top
    private int up(int i, int j) {
        check(i, j);
        int site = -1;
        if (i > 1 && i <= N && j > 0 && j <= N)
            site = this.site(i - 1, j);
        return site;
    }

    // site down to (row i, column j), return -1 if x bottom
    private int down(int i, int j) {
        check(i, j);
        int site = -1;
        if (i > 0 && i < N && j > 0 && j <= N) {
            site = this.site(i + 1, j);
        }
        return site;
    }

    // site left to (row i, column j),return -1 if has no left site
    private int left(int i, int j) {
        check(i, j);
        int site = -1;
        if (i > 0 && i <= N && j > 1 && j <= N) {
            site = this.site(i, j - 1);
        }
        return site;
    }

    // site right to (row i, column j),return -1 if has no right site
    private int right(int i, int j) {
        check(i, j);
        int site = -1;
        if (i > 0 && i <= N && j > 0 && j < N) {
            site = this.site(i, j + 1);
        }
        return site;
    }

    //
    private int site(int i, int j) {
        check(i, j);
        return (i - 1) * N + j;
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        if (!isOpen(i, j)) {
            int site = this.site(i, j);
            isOpen[site] = true;
            int up = this.up(i, j);
            if (up != -1 && isOpen[up]) {
                un.union(site, up);
            }
            int down = this.down(i, j);
            if (down != -1 && isOpen[down]) {
                un.union(site, down);
            }
            int left = this.left(i, j);
            if (left != -1 && isOpen[left]) {
                un.union(site, left);
            }
            int right = this.right(i, j);
            if (right != -1 && isOpen[right]) {
                un.union(site, right);
            }
            if (i == N && isFull(i, j)) {
                un.union(site, bottom);
            }
            // if (un.connected(site, bottom) && isFull(i, j)) {
            // isOpen[bottom] = true;
            // }
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        return isOpen[this.site(i, j)];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        int site = this.site(i, j);
        return un.connected(this.top, site) && isOpen[site];
    }

    // does the system percolate?
    public boolean percolates() {
        return un.connected(top, bottom);
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(3);
        percolation.open(1, 3);
        percolation.open(2, 3);
        percolation.open(3, 3);
        percolation.open(3, 1);

    } // test client (optional)
}
