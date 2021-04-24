package hw2;

import edu.princeton.cs.introcs.StdStats;
import edu.princeton.cs.introcs.StdRandom;

public class PercolationStats {
    private int T;
    private int N;
    private PercolationFactory pf;
    private double[] FractionArray;
    public PercolationStats(int N, int T, PercolationFactory pf){
        // perform T independent experiments on an N-by-N grid
        if (N <= 0 | T <= 0) throw new java.lang.IllegalArgumentException("Illegal Negative Argument");
        this.T = T;
        this.N = N;
        this.pf = pf;
        this.FractionArray = new double[T];
        simulate();
    }

    private void simulate(){
        for (int i = 0; i < T; i ++) {
            Percolation sample = pf.make(N);
            while (!sample.percolates()) {
                int col = StdRandom.uniform(0, N);
                int row = StdRandom.uniform(0, N);
                if (!sample.isOpen(col, row)) {
                    sample.open(col,row);
                }
            }
            double fraction = (double) sample.numberOfOpenSites() / (N * N);
            FractionArray[i] = fraction;
        }
    }
    public double mean() {
        // sample mean of percolation threshold
        return StdStats.mean(FractionArray);
    }
    public double stddev() {
        // sample standard deviation of percolation threshold
        return StdStats.stddev(FractionArray);
    }
    public double confidenceLow() {
        // low endpoint of 95% confidence interval
        return this.mean() - 1.96 * this.stddev()/java.lang.Math.sqrt(T);
    }
    public double confidenceHigh() {
        // high endpoint of 95% confidence interval
        return this.mean() + 1.96 * this.stddev()/java.lang.Math.sqrt(T);
    }

    public static void main(String[] args) {
        PercolationFactory pf = new PercolationFactory();
        PercolationStats result = new PercolationStats(2, 10000, pf);
        System.out.println(result.mean());

    }
}
