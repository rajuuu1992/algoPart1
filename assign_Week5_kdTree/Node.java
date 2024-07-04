import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;



public class Node implements Comparable<Node>  {
   private Point2D p;      // the point
   private RectHV rect;    // the axis-aligned rectangle corresponding to this node
   private Node lb;        // the left/bottom subtree
   private Node rt;        // the right/top subtree


   @Override
   public int compareTo(Node that) {
      /* YOUR CODE HERE */
      if (this.p.x() == that.p.x() && this.p.y() == that.p.y()) {
          return 0;
      }

      if (this.p.y() < that.p.y())
          return -1;
      if (this.p.y() > that.p.y())
          return 1;
      if (this.p.x() < that.p.x())
          return -1;
      if (this.p.x() > that.p.x())
          return 1;
      return 0;
  }

   // public Comparator<Node> slopeOrder() {
   //    /* YOUR CODE HERE */
   //    // @Override
   //    // int compare(Point a, Point b) {
   //    // return Double.compare(slopeTo(a), slopeTo(b));
   //    // };

   //    return new Comparator<Node>() {

   //       @Override
   //       public int compare(Node n1, Node n2) {
   //          return n1.compareTo(n2);
   //       }
   //    };
   // }
}