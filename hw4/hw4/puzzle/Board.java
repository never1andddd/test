package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

import java.nio.file.FileAlreadyExistsException;

public class Board implements WorldState {
    private int N;
    private int[][] tiles;
    private final int BLANK = 0;

    public Board(int[][] tiles) {
        N = tiles.length;
        this.tiles = new int[N][N];
        //Constructs a board from an N-by-N array of tiles where tiles[i][j] = tile at row i, column j
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    public int tileAt(int i, int j) {
        // Returns value of tile at row i, column j (or 0 if blank)
        return tiles[i][j];
    }

    public int size() {
        return N;
    }
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }

    private int convertTile(int i, int j) {
        return i * N + j + 1;
    } // e.g. 1，0 = 1*3+1； (0,0) = 1

    public int hamming() {
        // The number of tiles in the wrong position
        int returnVal = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tileAt(i, j) != convertTile(i, j) && tileAt(i, j) != BLANK) {
                    returnVal += 1;
                }
            }
        }
        return returnVal;
    }
    public int manhattan() {
        // The sum of the Manhattan distances (sum of the vertical and horizontal distance) from the
        // tiles to their goal positions.
        int returnVal = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int falseVal = tiles[i][j];
                if (falseVal != convertTile(i, j) && falseVal != BLANK) {
                    int TrueRow = (falseVal -1) / N;
                    int TrueCol = (falseVal - 1) % N;
                    int temp = Math.abs(TrueRow-i) + Math.abs(TrueCol-j);
                    returnVal += temp;
                }
            }
        }
        return returnVal;
    }
    public int estimatedDistanceToGoal() {
        // Estimated distance to goal. This method should simply return the results of manhattan()
        // when submitted to Gradescope
        return manhattan();
    }

    @Override
    public boolean equals(Object o) {
        // Returns true if this board's tile values are the same position as y's
        if (o == this) return true;
        if (o == null) return false;
        if (o.getClass() != this.getClass()) {
            return false;
        }
        Board that = (Board) o;
        if (that.N != this.N) return false;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (that.tiles[i][j] != this.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int uniqueHash = N;
        return uniqueHash;
    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
