
import java.util.ArrayList;
// import java.util.HashMap;

public class Board {

    private int[][] tiles;
    private int n;
    private int zeroI = -1;
    private int zeroJ = -1;
    // private int[][] hammings;
    // private int[][] manhattans;

    // private HashMap<Board, Integer> manhattanCache = new HashMap<Board,
    // Integer>();

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] inputTiles) {

        n = inputTiles.length;
        tiles = new int[n][n];
        // hammings = new int[n][n];
        // manhattans = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = inputTiles[i][j];
                if (tiles[i][j] == 0) {
                    zeroI = i;
                    zeroJ = j;
                }
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(Integer.toString(n));
        res.append("\n");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res.append(Integer.toString(tiles[i][j]));
                res.append(" ");
            }
            res.append("\n");
        }
        return res.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    private int getI(int elem) {
        return (elem - 1) / n;
    }

    private int getJ(int elem) {
        return (elem - 1) % n;
    }

    // number of tiles out of place
    public int hamming() {
        int hmg = 0;
        int count = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    continue;
                }
                if (i != getI(tiles[i][j]) || j != getJ(tiles[i][j])) {
                    ++hmg;
                }

            }
        }
        return hmg;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        // if (manhattanCache.containsKey(this)) {
        // return manhattanCache.get(this);
        // }

        int mht = 0;
        int count = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    continue;
                }
                int elem = tiles[i][j];
                int elemI = getI(elem);
                int elemJ = getJ(elem);

                // System.out.println(
                // " MH i j = " + i + "," + j + " and " + elem_i + " , " + elem_j
                // + " elem =" + tiles[i][j]);

                mht += Math.abs(elemI - i) + Math.abs(elemJ - j);
            }
        }
        // manhattanCache.putIfAbsent(this, mht);
        return mht;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == n - 1 && j == n - 1) {
                    return true;
                }
                if (tiles[i][j] != ++count) {
                    return false;
                }
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }

        Board newBoard = Board.class.cast(y);

        if (this.dimension() != newBoard.dimension()) {
            return false;
        }

        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == n - 1 && j == n - 1) {
                    return true;
                }
                if (tiles[i][j] != newBoard.at(i, j)) {
                    return false;
                }
            }
        }
        return false;
    }

    private void swap(int i, int j, int si, int sj) {
        int s = tiles[i][j];
        tiles[i][j] = tiles[si][sj];
        tiles[si][sj] = s;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> nbrs = new ArrayList<Board>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    if (j - 1 >= 0) {
                        swap(i, j, i, j - 1);
                        nbrs.add(new Board(tiles));
                        swap(i, j - 1, i, j);
                    }

                    if (j + 1 < n) {
                        swap(i, j, i, j + 1);
                        nbrs.add(new Board(tiles));
                        swap(i, j + 1, i, j);
                    }

                    if (i - 1 >= 0) {
                        swap(i, j, i - 1, j);
                        nbrs.add(new Board(tiles));
                        swap(i - 1, j, i, j);
                    }

                    if (i + 1 < n) {
                        swap(i, j, i + 1, j);
                        nbrs.add(new Board(tiles));
                        swap(i + 1, j, i, j);
                    }
                    return nbrs;
                }
            }
        }
        return nbrs;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board tw = new Board(tiles);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0 && tiles[i][(j + 1) % n] != 0) {
                    swap(i, j, i, (j + 1) % n);
                    tw = new Board(tiles);
                    swap(i, (j + 1) % n, i, j);
                    return tw;
                }
            }
        }
        return tw;
    }

    private int at(int i, int j) {
        if (i >= n || j >= n)
            return -1;

        return tiles[i][j];
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // int[][] tiles = { { 0, 3, 5 }, { 1, 7, 2 }, { 8, 6, 4 } };
        // int[][] tiles = { { 0, 3 }, { 2, 1 } };
        int[][] tiles = { { 1, 2, 3 }, { 5, 7, 6 }, { 4, 8, 0 } };
        /*
         * 0 3 5
         * 1 7 2
         * 8 6 4
         */
        Board b = new Board(tiles);
        System.out.println("  Hamming = " + Integer.toString(b.hamming()) +
                "  Mht = " + Integer.toString(b.manhattan()));

        System.out.println();

        Board tw = b.twin();

        System.out.println(b.toString());

        System.out.println("\n\nTWIN = \n");
        System.out.println(tw.toString());

        System.out.println("\n\nNEIGHTBORS = \n");

        Iterable<Board> it = b.neighbors();
        for (Board bb : it) {
            System.out.println(bb.toString());
        }
    }

}