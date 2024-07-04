import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.ArrayList;

public class PointSET {
   public         PointSET()                               // construct an empty set of points 
   {
        pointsTree = new SET<Node>();
        points = new ArrayList<Node>();
   }

   public boolean isEmpty()                      // is the set empty? 
   {
        return (points.size() == 0);

   }

   public int size()                         // number of points in the set 
   {
        return points.size();
   }

   public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
   {
        Node node = new Node(Point2D(p), null, null, null);
        points.add(node);

        pointsTree.add(node);

        
   }
   public boolean contains(Point2D p)            // does the set contain point p? 
   {
        Node node = new Node(Point2D(p), null, null, null);
        
        return pointsTree.contains(node);
   }

   public void draw()                         // draw all points to standard draw 
   {

   }

   public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary) 
   {
        for 
   }

   public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
   {

   }

   private final ArrayList<Node> points;

   edu.princeton.cs.algs4.SET<Node> pointsTree;
   
   

   public static void main(String[] args)                  // unit testing of the methods (optional) 
   {

   }
}

private static class Node {
    private Point2D p;      // the point
    private RectHV rect;    // the axis-aligned rectangle corresponding to this node
    private Node lb;        // the left/bottom subtree
    private Node rt;        // the right/top subtree
 }