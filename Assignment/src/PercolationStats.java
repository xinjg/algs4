public class PercolationStats {
//    private Percolation percolation;
    private int[] x;
    private double[] rate;
    private double mean = -1;
    private double stddev = -1;
    private double confidenceLo = -1;
    private double confidenceHi = -1;
    private int T;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N < 1)
            throw new java.lang.IllegalArgumentException("" + N
                    + " N must be integers greater than 0");
        if (T < 1)
            throw new java.lang.IllegalArgumentException("" + T
                    + " T must be integers greater than 0");
        this.T = T;
        x = new int[T];
        rate = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation percolation = new Percolation(N);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(N) + 1;
                int col = StdRandom.uniform(N) + 1;
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                    x[i]++;
                }
            }
            percolation = null;
            rate[i] = (double) x[i] / (double) (N * N);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        double sum = 0;
        for (int i = 0; i < rate.length; i++) {
            sum += rate[i];
        }
        this.mean = sum / (double) T;
        return this.mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double sum = 0;
        mean();
        for (int i = 0; i < rate.length; i++) {
            sum += Math.pow(rate[i] - mean, 2.0);
        }
        stddev = Math.pow(sum / (T - 1.0), 0.5);
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        if (confidenceLo == -1) {
            confidenceLo = mean - 1.96 * stddev / Math.pow(T, 0.5);
        }
        return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        if (confidenceHi == -1) {
            confidenceHi = mean + 1.96 * stddev / Math.pow(T, 0.5);
        }
        return confidenceHi;
    }

    // test client (described below)

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(N, T);
        StdOut.println("mean                                 = "
                + percolationStats.mean());
        StdOut.println("stddev                               = "
                + percolationStats.stddev());
        StdOut.println("95% confidence interval = "
                + percolationStats.confidenceLo() + " , "
                + percolationStats.confidenceHi());
    }
}
