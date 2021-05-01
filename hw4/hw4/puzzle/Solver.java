package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.*;

public class Solver {
    //Keep a priority queue of “move sequences”.
    private MinPQ<Node> moveSeq;
    private Deque w = new ArrayDeque();
    private boolean findGoal = false;
    private Node goal;

    /**
     * Constructor which solves the puzzle, computing everything necessary for moves() and solution() to
     * not have to solve the problem again. Solves the puzzle using the A* algorithm. Assumes a solution exists.
     */
    public Solver(WorldState initial) {
        Node first = new Node(initial, 0, null);
        moveSeq = new MinPQ<>();
        moveSeq.insert(first);

        while (!findGoal) {
            //Remove the “best” move sequence from the PQ, let’s call it BMS.
            Node BMS = moveSeq.delMin();
            //Let F be the last state in BMS.
            WorldState F = BMS.w;
            if (F.isGoal()) {
                //If F is the goal state, you’re done, so return BMS.
                goal = BMS;
                findGoal = true;
                break;
            }
            // If F is not the goal, then for each neighbor N of F,
            // create a new move sequence that consists of BMS + N and put it in the PQ.
            Node prev = BMS.came_from;
            for (WorldState N : F.neighbors()) {
                int new_cost = BMS.cost_so_far + 1;
                Node newNode = new Node(N, new_cost, BMS);
                if (prev == null | (prev != null && !N.equals(prev.w))) {
                    moveSeq.insert(newNode);
                }
            }
        }
        while (goal != null) {
            w.addFirst(goal.w);
            goal = goal.came_from;
        }
    }

    //Returns the minimum number of moves to solve the puzzle starting at the initial WorldState.
    public int moves() {
        int minMove = w.size() - 1;
        return minMove;
    }

    //Returns a sequence of WorldStates from the initial WorldState to the solution.
    public Iterable<WorldState> solution() {
        return w;
    }

    private class Node implements Comparable<Node> {
        private WorldState w; //a WorldState.
        private int cost_so_far; //the number of moves made to reach this world state from the initial state
        private Node came_from; //a reference to the previous search node.
        private int priority;

        Node(WorldState w, int n, Node came_from) {
            this.w = w;
            this.cost_so_far = n;
            this.came_from = came_from;
            this.priority = cost_so_far + w.estimatedDistanceToGoal();
        }

        public int compareTo(Node o) {
            return this.priority - o.priority;
        }
    }
}
