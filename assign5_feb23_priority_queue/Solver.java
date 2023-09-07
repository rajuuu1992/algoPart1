
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private Board init;
    private int movesMade = -1;
    private ArrayList<Priority> result = new ArrayList<>();
    private ArrayList<Board> finalResult = new ArrayList<Board>();

    private int maxMoves = 0;
    private boolean twinSolution = false;
    private boolean solvable = false;

    // private class HammingComparator implements Comparator<Board> {
    // @Override
    // public int compare(Board b1, Board b2) {
    // return Integer.compare(b1.hamming(), b2.hamming());
    // }
    // }

    private class Priority {
        public Board b;
        public int p;
        private int movesMade;
        private int mht;
        public Priority prev;

        Priority(Board bb, int mh, int mvs, Priority prevBoard) {
            b = bb;
            mht = mh;
            movesMade = mvs;
            p = mht + movesMade;
            prev = prevBoard;
        }
    }

    private class ManhattanComparator implements Comparator<Priority> {
        @Override
        public int compare(Priority b1, Priority b2) {
            if (b1.p == b2.p) {
                return Integer.compare(b1.mht, b2.mht);
            }
            return Integer.compare(b1.p, b2.p);
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Board null");
        }

        init = initial;
        maxMoves = init.dimension() * init.dimension() * (init.dimension()) * (init.dimension());
        if (maxMoves > 4000000) {
            maxMoves = 4000000;
        }
        // System.out.println("Max moves = " + maxMoves);

        solvable = solve(initial);

        if (solvable) {
            for (Priority p = result.get(result.size() - 1); p != null && p.b != null; p = p.prev) {
                finalResult.add(p.b);
            }
            movesMade = finalResult.size() - 1;
            Collections.reverse(finalResult);
        }
    }

    private boolean solve(Board input) {
        if (twinSolution == true) {
            return false;
        }
        twinSolution = true;

        if (input.isGoal()) {
            // System.out.println("!!!!!Goal no operation");
            result.add(new Priority(input, input.manhattan(), 0, null));
            return true;
        }
        MinPQ<Priority> minpq = new MinPQ<>(new ManhattanComparator());

        // System.out.println(" Board = " + input.toString() + " , Mh = " +
        // input.manhattan());
        Priority p = new Priority(input, input.manhattan(), 0, null);

        minpq.insert(p);

        while (minpq.size() > 0) {
            if (movesMade < 100)
                System.out.println("\n----------------------\n");
            Priority mini = minpq.delMin();
            ++movesMade;
            // System.out.println(" Move = " + mini.toString());
            if (movesMade < 100)
                System.out.println(
                        " DEQUEUED = " + mini.b.toString() + " , P = " + mini.p + "  mH= " + mini.b.manhattan()
                                + " Move = " + mini.movesMade);

            result.add(mini);
            if (mini.b.isGoal()) {
                return true;
            }

            if (movesMade > maxMoves) {
                result.clear();
                movesMade = 0;
                return solve(input.twin());
            }

            Iterable<Board> nbrs = mini.b.neighbors();

            for (Board b : nbrs) {
                if (!b.equals(mini.b)) {
                    Priority pp = new Priority(b, b.manhattan(), mini.movesMade + 1, mini);
                    minpq.insert(pp);
                    if (movesMade < 100)
                        System.out.println(
                                " ENQUEUED = " + b.toString() + " , P = " + pp.p + "  mH= " + pp.mht
                                        + " Move = " + pp.movesMade);

                }
            }
        }
        return false;

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return movesMade;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return finalResult;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        // In in = new In(args[0]);
        // int n = in.readInt();
        // int[][] tiles = new int[n][n];
        // for (int i = 0; i < n; i++) {
        // for (int j = 0; j < n; j++) {
        // tiles[i][j] = in.readInt();
        // }
        // }
        // int[][] tiles = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
        // int[][] tiles = { { 1, 0 }, { 3, 2 } };
        // int[][] tiles = { { 1, 0 }, { 3, 2 } };

        // int[][] tiles = { { 1, 2, 3 }, { 5, 7, 6 }, { 4, 8, 0 } };
        // int[][] tiles = { { 1, 0, 2 }, { 7, 5, 4 }, { 8, 6, 3 } };

        int[][] tiles = { { 5, 1, 8 }, { 2, 7, 3 }, { 4, 0, 6 } };
        Board initial = new Board(tiles);

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