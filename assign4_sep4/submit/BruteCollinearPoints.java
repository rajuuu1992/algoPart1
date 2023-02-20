import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
   private int length = 0;
   private Point[] myPoints;
   private LineSegment[] finalResult;

   public BruteCollinearPoints(Point[] points) // finds all line segments containing 4 points
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
               // System.out.println("......Equal pts = " + points[i].toString() + " and " +
               // points[j].toString());
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

   public LineSegment[] segments() // the line segments
   {
      if (finalResult != null) {
         LineSegment[] ret = new LineSegment[finalResult.length];
         System.arraycopy(finalResult, 0, ret, 0, finalResult.length);
         return ret;
      }
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
      // for (int i = 0; i < len; i++) {
      // System.out.print(" --> " + myPoints[i].toString());
      // }

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
      int cc = 0;
      for (int i = 0; i < result.length; i++) {
         if (result[i] != null)
            ++cc;
      }

      finalResult = new LineSegment[cc];
      int j = 0;
      for (int i = 0; i < result.length; i++) {
         if (result[i] != null)
            finalResult[j++] = result[i];
      }

      LineSegment[] ret = new LineSegment[finalResult.length];
      System.arraycopy(finalResult, 0, ret, 0, finalResult.length);
      return ret;
   }

   public static void main(String[] args) {
      /* YOUR CODE HERE */
      // 10000 0
      // 0 10000
      // 3000 7000
      // 7000 3000
      // 20000 21000
      // 3000 4000
      // 14000 15000
      // 6000 7000

      Point inputPoints[] = new Point[] {
            new Point(10000, 0),
            new Point(0, 10000),
            new Point(3000, 7000),
            new Point(700, 3000),
            new Point(20000, 21000),
            new Point(3000, 4000),
            new Point(14000, 15000),
            new Point(6000, 7000),
      };

      // draw the points
      // StdDraw.enableDoubleBuffering();
      // StdDraw.setXscale(0, 32768);
      // StdDraw.setYscale(0, 32768);
      // for (Point p : inputPoints) {
      // p.draw();
      // }
      // StdDraw.show();
      // inputPoints[0] = new Point(4, 4);
      // inputPoints[1] = new Point(3, 3);
      // inputPoints[2] = new Point(2, 2);
      // inputPoints[3] = new Point(1, 1);
      // inputPoints[4] = new Point(0, 0);
      // inputPoints[5] = new Point(5, 5);
      // int retry = 10;
      // BruteCollinearPoints bc = new BruteCollinearPoints(inputPoints);
      // while (retry-- > 0) {
      // bc.numberOfSegments();

      // for (LineSegment s : bc.segments()) {
      // System.out.println("->" + s);
      // if (s == null)
      // System.out.println("-> NULLLLLL..");
      // }
      // System.out.println("Len0 = " + bc.segments().length + " num seg = " +
      // bc.numberOfSegments());
      // }
   }

   // public static void main(String[] args) {

   // // read the n points from a file

   // int n = in.readInt();
   // Point[] points = new Point[n];
   // for (int i = 0; i < n; i++) {
   // int x = in.readInt();
   // int y = in.readInt();
   // points[i] = new Point(x, y);
   // }

   // // draw the points
   // StdDraw.enableDoubleBuffering();
   // StdDraw.setXscale(0, 32768);
   // StdDraw.setYscale(0, 32768);
   // for (Point p : points) {
   // p.draw();
   // }
   // StdDraw.show();

   // // print and draw the line segments
   // BruteCollinearPoints collinear = new BruteCollinearPoints(points);
   // for (LineSegment segment : collinear.segments()) {
   // System.out.println(segment);
   // segment.draw();
   // }
   // StdDraw.show();
   // }

}