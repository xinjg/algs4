public class PercolationStats {
    private double mean = -1;
    private double stddev = -1;
    private double confidenceLo = -1;
    private double confidenceHi = -1;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N < 1)
            throw new java.lang.IllegalArgumentException("" + N
                    + " N must be integers greater than 0");
        if (T < 1)
            throw new java.lang.IllegalArgumentException("" + T
                    + " T must be integers greater than 0");
        int[] x = new int[T];
        double[] rate = new double[T];
        double rateSum = 0; // to calculate mean
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
            rateSum += rate[i]; 
        }
        // Calculate mean
        this.mean = rateSum / (double) T; 
        // Calculate stddev
        double sum = 0; 
        for (int i = 0; i < rate.length; i++) {
            sum += Math.pow(rate[i] - mean, 2.0);
        }
        this.stddev = Math.pow(sum / (T - 1.0), 0.5);
        // Calculate confidenceLo
        this.confidenceLo = mean - 1.96 * stddev / Math.pow(T, 0.5);
        // Calculate confidenceHi
        this.confidenceHi = mean + 1.96 * stddev / Math.pow(T, 0.5);
    }

    // sample mean of percolation threshold
    public double mean() {
        return this.mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return this.stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.confidenceHi;
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
