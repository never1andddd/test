import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Picture;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;

public class SeamCarver {
    private Picture p;
    private int width;
    private int height;
    private MinPQ<Pos> moveSeq;
    private boolean findGoal = false;

    public SeamCarver(Picture picture) {
        this.p = picture;
        this.width = picture.width();
        this.height = picture.height();
    }

    public Picture picture() {
        // current picture
        return new Picture(this.p);
    }
    public int width() {
        // width of current picture
        return width;
    }
    public int height() {
        // height of current picture
        return height;
    }

    private int plus(int x, int limit) {
        if (x + 1 == limit) {
            return 0;
        }
        return x + 1;
    }

    private int minus(int x, int limit) {
        if (x - 1 < 0) {
            return limit - 1;
        }
        return x - 1;
    }
    public double energy(int x, int y) {
        if (x<0 | x >= width | y<0 | y >=height) {
            throw new java.lang.IndexOutOfBoundsException("Index out of bound!");
        }
        // energy of pixel at column x and row y
        Color x_left = p.get(minus(x, width),y);
        Color x_right = p.get(plus(x, width),y);
        Color y_up = p.get(x,plus(y, height));
        Color y_down = p.get(x,minus(y, height));
        int Rx = x_left.getRed() - x_right.getRed();
        int Gx = x_left.getGreen() - x_right.getGreen();
        int Bx = x_left.getBlue() - x_right.getBlue();
        int Ry = y_up.getRed() - y_down.getRed();
        int Gy = y_up.getGreen() - y_down.getGreen();
        int By = y_up.getBlue() - y_down.getBlue();
        double x_square = Rx * Rx + Gx * Gx + Bx * Bx;
        double y_square = Ry * Ry + Gy * Gy + By * By;
        return x_square + y_square;
    }

    public int[] findVerticalSeam() {
        // sequence of indices for vertical seam
        int[] w = new int[height];
        double minCost = Double.MAX_VALUE;
        for (int i = 0; i < width; i ++) {
            Pos first = new Pos(i, 0, energy(i, 0),null);
            moveSeq = new MinPQ<>();
            moveSeq.insert(first);
            Pos last = findMinPath(i);
            if (last.cost_so_far<minCost) {
                minCost = last.cost_so_far;
                for (int j = height - 1; j>= 0; j--) {
                    w[j] = last.x;
                    last = last.came_from;
                }
            }
        }
        return w;
    }

    private Pos findMinPath(int x) {
        Pos last;
        int goal = height - 1;
        int[] neighbors;

        findGoal = false;
        while (!findGoal) {
            //Remove the “best” move sequence from the PQ, let’s call it BMS.
            Pos BMS = moveSeq.delMin();
            if (BMS.x == 0) {
                neighbors = new int[]{0, 1};
            } else if (BMS.x == width - 1) {
                neighbors = new int[]{-1, 0};
            } else {
                neighbors = new int[]{-1, 0, 1};
                //System.out.println("x is " + x + "neighbors include" + neighbors[1] + ", " + neighbors[2]);
            }
            moveSeq = new MinPQ<>();
            //Let F be the last state in BMS.
            if (BMS.y == goal) {
                //If F is the goal state, you’re done, so return BMS.
                findGoal = true;
                return BMS;
            }
            // If F is not the goal, then for each neighbor N of F,
            // create a new move sequence that consists of BMS + N and put it in the PQ.
            Pos prev = BMS.came_from;
            for (int i : neighbors) {
                double new_cost = BMS.cost_so_far + energy(BMS.x + i,BMS.y+1);
                Pos newPos = new Pos(BMS.x+i, BMS.y+1, new_cost, BMS);
                moveSeq.insert(newPos);
            }
        }
        return null;
    }

    public int[] findHorizontalSeam() {
        Picture transP = new Picture(height, width);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color transC= p.get(j,i);
                transP.set(i, j, transC);
            }
        }
        SeamCarver trans = new SeamCarver(transP);
        int[] w = trans.findVerticalSeam();
        return w;
    }

    public void removeHorizontalSeam(int[] seam) {
        // remove horizontal seam from picture
        if (seam.length != width) {
            throw new java.lang.IllegalArgumentException("Length is wrong!");
        }
        for (int i=1; i <seam.length; i++) {
            //System.out.println(i + "th is " + seam[i] );
            if (Math.abs(seam[i] - seam[i-1]) > 1) {
                throw new java.lang.IllegalArgumentException("2 consecutive numbers differ more than 1");
            }
        }
        this.p = new Picture(SeamRemover.removeHorizontalSeam(this.p, seam));
        height--;
    }
    public void removeVerticalSeam(int[] seam) {
        if (seam.length != height) {
            throw new java.lang.IllegalArgumentException("Length is wrong!");
        }
        for (int i=1; i <seam.length; i++) {
            if (Math.abs(seam[i] - seam[i-1]) > 1) {
                throw new java.lang.IllegalArgumentException("2 consecutive numbers differ more than 1");
            }
        }
        // remove vertical seam from picture
        this.p = new Picture(SeamRemover.removeVerticalSeam(this.p, seam));
        width--;

    }

    private class Pos implements Comparable<Pos> {
        public int y;
        public int x; //current position
        public double cost_so_far; //cost made to reach this position from the initial position
        public Pos came_from; //a reference to the previous position.

        Pos(int x, int y, double n, Pos came_from) {
            this.x = x;
            this.y = y;
            this.cost_so_far = n;
            this.came_from = came_from;
        }

        public int compareTo(Pos o) {
            return (int)(this.cost_so_far - o.cost_so_far);
        }

    }
}