import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private int length = 0;
    private Point[] myPoints;
    private LineSegment[] finalResult;

    public FastCollinearPoints(Point[] points) // finds all line segments containing 4 points
    {
        if (points == null) {
            throw new IllegalArgumentException("Points is null");
        }

        length = points.length;
        int l = length;
        for (int i = 0; i < l; i++)
            if (points[i] == null)
                throw new IllegalArgumentException("One of the points of null");

        for (int i = 0; i < l; i++) {
            for (int j = i + 1; j < l; j++) {
                if (i == j)
                    continue;
                if (0 == points[i].compareTo(points[j])) {
                    throw new IllegalArgumentException("Points equal ");
                }
            }
        }
        myPoints = new Point[length];
        for (int i = 0; i < length; i++)
            myPoints[i] = points[i];
    }

    public int numberOfSegments() // the number of line segments
    {
        finalResult = segments();
        return finalResult.length;
    }

    private void merge_arr(Integer[] arr, int low, int high, int checkPoint) {
        if (low >= high)
            return;
        int mid = low + (high - low) / 2;

        // System.out.println("\nMerge input low = " + low + " high = " + high + " mid =
        // " + mid);
        // for (int i = low; i <= high; i++) {
        // System.out.print(" -->" + arr[i]);
        // }

        Integer[] temp = new Integer[arr.length];
        int x = low, y = mid + 1;

        for (int i = 0; i < arr.length; i++) {
            temp[i] = arr[i];
        }
        int z = low;
        for (int i = 0; i <= high - low + 1 && x <= mid && y <= high; i++) {
            if (myPoints[checkPoint].slopeTo(myPoints[arr[x]]) < myPoints[checkPoint].slopeTo(myPoints[arr[y]])) {
                temp[z] = arr[x++];
            } else {
                temp[z] = arr[y++];
            }
            z++;
        }

        while (x > mid && y < high) {
            temp[z++] = arr[y++];
        }

        while (x <= mid && y >= high) {
            temp[z++] = arr[x++];
        }

        for (int i = 0; i < temp.length; i++) {
            arr[i] = temp[i];
        }

        // System.out.println("\nMerge Output = ");
        // for (int i = low; i <= high; i++) {
        // System.out.print(" -->" + arr[i] + "(" +
        // myPoints[checkPoint].slopeTo(myPoints[arr[i]]) + ")");
        // }

    }

    private void msort(Integer[] arr, int low, int high, int checkPoint) {
        if (low >= high)
            return;

        // System.out.println("\nInput array len = " + arr.length);
        // for (int i = 0; i < arr.length; i++) {
        // System.out.print(" -->" + arr[i]);
        // }
        int mid = low + (high - low) / 2;

        if (low < high) {

            msort(arr, low, mid, checkPoint);
            msort(arr, mid + 1, high, checkPoint);
            merge_arr(arr, low, high, checkPoint);
        }
        // System.out.println("\nArray len = " + arr.length);
        // for (int i = 0; i < arr.length; i++) {
        // System.out.print(" -->" + arr[i]);
        // }
    }

    public LineSegment[] segments() // the line segments
    {
        if (finalResult != null) {
            LineSegment[] ret = new LineSegment[finalResult.length];
            System.arraycopy(finalResult, 0, ret, 0, finalResult.length);
            return ret;
        }
        int len = myPoints.length;

        LineSegment[] result = new LineSegment[len];
        ArrayList<LineSegment> rs = new ArrayList<LineSegment>();
        Arrays.sort(myPoints);
        for (int i = 0; i < len; i++) {
            ArrayList<Integer> temp_slope_arr = new ArrayList<Integer>();

            for (int j = i + 1; j < len; j++) {
                temp_slope_arr.add(j);
            }
            Integer[] temp_slope = new Integer[temp_slope_arr.size()];
            int z = 0;
            for (Integer dd : temp_slope_arr) {
                temp_slope[z] = dd;
                ++z;
            }
            msort(temp_slope, 0, z - 1, i);
            int match = 0;
            // System.out.println();
            // for (Integer d : temp_slope) {
            // System.out.print(" -***->" + d);
            // }

            for (int x = 1; x < temp_slope.length; x++) {
                if (myPoints[x - 1].compareTo(myPoints[i]) == 0)
                    continue;

                Double curr = myPoints[i].slopeTo(myPoints[temp_slope[x]]);
                Double prev = myPoints[i].slopeTo(myPoints[temp_slope[x - 1]]);

                if (curr.compareTo(prev) == 0) {
                    // System.out.println("\nInput i = " + myPoints[i] + " Slope eq = " + "curr = "
                    // + myPoints[x]
                    // + "Slope = " + curr + " prev = " + myPoints[x - 1] + " slope = " + prev);
                    ++match;
                } else {
                    if (match >= 2) {
                        LineSegment s = new LineSegment(myPoints[i], myPoints[x]);
                        rs.add(s);
                    }
                    match = 0;
                }
            }
            if (match >= 2) {
                LineSegment s = new LineSegment(myPoints[i], myPoints[temp_slope.length - 1]);
                rs.add(s);

            }
        }

        ArrayList<LineSegment> temp_res = new ArrayList<LineSegment>();

        for (LineSegment ss : rs) {
            if (ss == null)
                continue;
            boolean found = false;
            for (int i = 0; i < temp_res.size(); i++) {

                if (temp_res.get(i).equals(ss)) {
                    found = true;
                    break;
                }
            }
            if (found == false)
                temp_res.add(ss);
        }

        finalResult = new LineSegment[temp_res.size()];
        int j = 0;
        for (int i = 0; i < temp_res.size(); i++) {
            if (temp_res.get(i) != null) {
                finalResult[j++] = temp_res.get(i);
                // System.out.print(" -------->" + temp_res.get(i));
            }

        }

        LineSegment[] ret = new LineSegment[finalResult.length];
        System.arraycopy(finalResult, 0, ret, 0, finalResult.length);
        return ret;
    }

    public static void main(String[] args) {
        /* YOUR CODE HERE */

        // Point inputPoints[] = new Point[5];
        // inputPoints[0] = new Point(4, 4);
        // inputPoints[1] = new Point(3, 3);
        // inputPoints[2] = new Point(2, 2);
        // inputPoints[3] = new Point(1, 1);
        // inputPoints[4] = new Point(0, 0);
        Point[] inputPoints = new Point[] {
                new Point(10000, 0),
                new Point(0, 10000),
                new Point(3000, 7000),
                new Point(700, 3000),
                new Point(20000, 21000),
                new Point(3000, 4000),
                new Point(14000, 15000),
                new Point(6000, 7000),
        };
        // Point[] inputPoints = new Point[] {
        // new Point(1, 2),
        // new Point(3, 4),
        // new Point(5, 6),
        // new Point(7, 8),
        // new Point(9, 10),
        // };
        FastCollinearPoints bc = new FastCollinearPoints(inputPoints);
        for (LineSegment ss : bc.segments()) {
            System.out.print("-->" + ss);
        }
    }

}