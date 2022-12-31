
public class FastCollinearPoints {
    private int length = 0;
    private Point[] myPoints;

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
                    System.out.println("......Equal pts = " + points[i].toString() + " and " +
                            points[j].toString());
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
        return length;
    }

    public LineSegment[] segments() // the line segments
    {
        int len = myPoints.length;

        LineSegment[] result = new LineSegment[len];
        int res = 0;

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (myPoints[i].compareTo(myPoints[j]) < 0) {

                    Point swap = myPoints[i];
                    myPoints[i] = myPoints[j];
                    myPoints[j] = swap;
                }
            }
        }
        for (int i = 0; i < len; i++) {
            System.out.print(" --> " + myPoints[i].toString());
        }

        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {

                if (i == j)
                    continue;
                double ij = myPoints[i].slopeTo(myPoints[j]);

                for (int k = j + 1; k < len; k++) {
                    double ik = myPoints[i].slopeTo(myPoints[k]);

                    if (ij != ik)
                        continue;

                    for (int l = k + 1; l < len; l++) {
                        double il = myPoints[i].slopeTo(myPoints[l]);

                        if (ij == ik && ij == il) {
                            LineSegment ll = new LineSegment(myPoints[i], myPoints[l]);
                            boolean add = true;
                            for (int z = 0; z < len; z++) {
                                if (result[z] == ll) {
                                    add = false;
                                    break;
                                }
                            }
                            if (add)
                                result[res++] = ll;
                        }
                    }
                }
            }
        }
        System.out.println("RES");
        for (int i = 0; i < res; i++) {
            System.out.print(" |||-- >" + result[i].toString());
        }
        return result;
    }

    public static void main(String[] args) {
        /* YOUR CODE HERE */

        Point inputPoints[] = new Point[5];
        inputPoints[0] = new Point(4, 4);
        inputPoints[1] = new Point(3, 3);
        inputPoints[2] = new Point(2, 2);
        inputPoints[3] = new Point(1, 1);
        inputPoints[4] = new Point(0, 0);
        FastCollinearPoints bc = new FastCollinearPoints(inputPoints);
        bc.segments();
    }

}