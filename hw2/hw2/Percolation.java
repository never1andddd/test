package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.introcs.StdRandom;

public class Percolation {
    public int N;
    private WeightedQuickUnionUF p;
    public Boolean[][] grid;
    public int opened;
    public Percolation(int N) {
        // create N-by-N grid, with all sites initially blocked
        if (N <= 0) throw new java.lang.IllegalArgumentException("Illegal argument");
        this.grid = new Boolean[N][N];
        this.N = N;
        this.opened = 0;
        this.p = new WeightedQuickUnionUF(N * N + 2);
        for (int i = 0; i < N; i ++){
            for (int j = 0; j < N; j ++){
                grid[i][j] = Boolean.FALSE;
                if (i == 0) {
                    p.union(xyTo1D(i, j), N * N);
                }
                if (i == N - 1) {
                    p.union(xyTo1D(i, j), N * N + 1);
                }
            }
        }
    }

    // convert grid number to index
    public int xyTo1D(int row, int col){
        int index = row * N + col;
        return index;
    }

    // check if the row and col number is valid
    public boolean validIndex(int row, int col){
        if (row >= 0 && row <= N - 1 && col >= 0 && col <= N - 1){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public void connectTogether(int row, int col, int row2, int col2) {
        if (validIndex(row2, col2)) {
            if (isOpen(row2, col2)) p.union(xyTo1D(row, col), xyTo1D(row2, col2));
        }
    }

    public void open(int row, int col) {
        // open the site (row, col) if it is not open already
        if (!validIndex(row, col))
            throw new java.lang.IndexOutOfBoundsException("Index out of bound");
        grid[row][col] = Boolean.TRUE;
        opened += 1;
        connectTogether(row,col, row + 1, col);
        connectTogether(row,col, row - 1, col);
        connectTogether(row,col, row, col + 1);
        connectTogether(row,col, row, col - 1);
    }

    public boolean isOpen(int row, int col) {
        // is the site (row, col) open?
        if (!validIndex(row, col))
            throw new java.lang.IndexOutOfBoundsException("Index out of bound");
        return grid[row][col];
    }
    public boolean isFull(int row, int col) {
        // is the site (row, col) full?
        if (!validIndex(row, col))
            throw new java.lang.IndexOutOfBoundsException("Index out of bound");
        return p.connected(xyTo1D(row, col),N * N) && isOpen(row, col);
    }
    public int numberOfOpenSites() {
        // number of open sites
        return opened;
    }
    public boolean percolates() {
        // does the system percolate?
        return p.connected(N * N, N * N + 1);
    }
    public static void main(String[] args) {

        int N = 5;
        Percolation sample = new Percolation(N);
        //System.out.println(sample.percolates());
        //System.out.println("is 0,0 connected with top?" + sample.p.connected(25, 26));
        while (!sample.percolates()) {
            int col = StdRandom.uniform(0, N);
            int row = StdRandom.uniform(0, N);
            if (!sample.isOpen(row, col)) sample.open(row, col);
            //System.out.println("row" + row + ", col: " + col);
        }
        double fraction = (double) sample.opened / (N * N);
        System.out.println(sample.numberOfOpenSites());
        System.out.println(fraction);

    }  // use for unit testing (not required)
}