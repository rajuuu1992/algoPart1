import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import java.util.ArrayList;
import java.util.Iterator;



public class PointSET {

	private final SET<Point2D> pointsTree;

	public PointSET()                               // construct an empty set of points 
	{
		pointsTree = new SET<Point2D>();
	}

	public boolean isEmpty()                      // is the set empty? 
	{
		return (pointsTree.size() == 0);

	}

	public int size()                         // number of points in the set 
	{
		return pointsTree.size();
	}

	public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
	{
		if (p == null) {
			throw new IllegalArgumentException();
		}
		if (this.contains(p)) {
			return;
		}

		pointsTree.add(p);
	}
	public boolean contains(Point2D p)            // does the set contain point p? 
	{
		if (p == null) {
			throw new IllegalArgumentException();
		}

		return pointsTree.contains(p);
	}

	public void draw()                         // draw all points to standard draw 
	{

		//		System.out.println("Draw called....TBD\n");
	}

	public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary) 
	{
		if (rect == null) {
			throw new IllegalArgumentException();
		}
		ArrayList<Point2D> res = new ArrayList<Point2D>();

		Iterator<Point2D> points = pointsTree.iterator();
		while (points .hasNext())
		{
			Point2D p = points.next();

			if (rect.contains(p))
			{
				res.add(p);
			}
		}

		return res;
	}

	public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
	{
		if (p == null) {
			throw new IllegalArgumentException();
		}
		double min = Integer.MAX_VALUE;
		Point2D res = null;

		Iterator<Point2D> points= pointsTree.iterator();
		while (points.hasNext()) {
			Point2D n = points.next();
			double dis = p.distanceSquaredTo(p);
			if (dis < min) {
				min = dis;
				res = p;
			}
		}

		return res;
	}

	public static void main(String[] args)                  // unit testing of the methods (optional) 
	{
		//	   System.out.println("H>.. Point\n");
		//	   PointSET p = new PointSET();
		//	   Point2D pp = new Point2D(0.2, 0.3);
		//	   Point2D qq = new Point2D(0.1, 0.3);
		//	   p.insert(pp);
		//	   
		//	   System.out.println("Contains = ");
		//	   System.out.println(p.contains(pp));
		//	   
		//	   System.out.print("\nContains = ");
		//	   System.out.println(p.contains(qq));
		//	   
		//	   p.insert(qq);
		//	   
		//	   
		//	   System.out.print("\nNearest= ");
		//	   System.out.println(p.nearest(pp));
		//	   
		//	   System.out.print("\nNearest= ");
		//	   System.out.println(p.nearest(qq));
		//	   
		//	   p.draw();
	}	



//	private static class Node implements Comparable<Node>  {
//		private Point2D p;      // the point
//
//		Node(Point2D pp) {
//			p = pp;
//		}
//
//
//		@Override
//		public int compareTo(Node that) {
//			/* YOUR CODE HERE */
//			if (this.p.x() == that.p.x() && this.p.y() == that.p.y()) {
//				return 0;
//			}
//
//			if (Double.compare(this.p.y(), that.p.y()) < 0)
//				return -1;
//			if (Double.compare(this.p.y(), that.p.y()) > 0)
//				return 1;
//			if (Double.compare(this.p.x(), that.p.x()) < 0)
//				return -1;
//			if (Double.compare(this.p.x(), that.p.x()) > 0)
//				return 1;
//			return 0;
//		}
//	}
}
