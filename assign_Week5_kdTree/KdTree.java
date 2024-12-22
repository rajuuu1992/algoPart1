import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;



public class KdTree {

	Node root = null;
	int sz = 0;
	public KdTree()                               // construct an empty set of points 
	{
	}

	public boolean isEmpty()                      // is the set empty? 
	{
		return root == null;
	}

	public int size()                         // number of points in the set 
	{
		return sz;
	}

	public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
	{
		if (p == null) {
			throw new IllegalArgumentException();
		}

		if (this.contains(p)) {
			return;
		}
		sz++;

		if (root == null ) {
			root = new Node(p, null, null, null, false);
			System.out.println("\n ROOTE INSERT = " + root.Data());
			return;
		}

		if (p.x() < root.p.x()) {
			root.left = insertRecur(root.left, p, true);
		} else if (p.x() > root.p.x()) {
			root.right = insertRecur(root.right,p, true);
		} else {
			if (p.y() < root.p.y()) {
				root.left = insertRecur(root.left, p, true);
			} else {
				root.right= insertRecur(root.right, p, true);
			}
		}
	}

	private Node insertRecur(Node node, Point2D p, boolean hori) {
		if (node == null) {
			System.out.println("Inserting + " + p.toString() + "   HORI = " + hori);
			Node newNode = new Node(p, null, null, null, hori);
			return newNode;
		}

		double x = node.p.x(), y= node.p.y();
		if (node.hori) {
			x = node.p.y();
			y = node.p.x();
		}
		if (p.x() < x) {
			node.left = insertRecur(node.left, p, !hori);
			return node;
		} else if (p.x() > x) {
			node.right = insertRecur(node.right, p, !hori);
			return node;
		} else {
			if (p.y() < y) {
				node.left = insertRecur(node.left, p, !hori);
				return node;
			} else {
				node.right = insertRecur(node.right, p, !hori);
				return node;
			}
		}	
	}



	public boolean contains(Point2D p)            // does the set contain point p? 
	{
		if (p == null) {
			throw new IllegalArgumentException();
		}

		if (root == null) {
			return false;
		}

		Node node = root;
		while (node != null) {
			if (node.p.equals(p)) {
				return true;
			}
			if (p.x() < node.p.x()) {
				node = node.left;
			} else if (p.x() > node.p.x()){
				node = node.right;
			} else {
				if (p.y() < node.p.y()) {
					node = node.left;
				} else {
					node = node.right;
				}
			}
		}
		return false;
	} 
	
	private void inorder(Node root) {
		if (root == null) {
			return;
		}
		inorder(root.left);
		System.out.print("--->" + root.Data());
		inorder(root.right);
	}

	public void draw()                         // draw all points to standard draw 
	{

		
		if (root == null) {
			return;
		}
		System.out.println("Draw called....Root = " + root.Data());
		inorder(root);
	}

	public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary) 
	{
	}

	public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
	{
		if (p == null) {
			throw new IllegalArgumentException();
		}
	}

	public static void main(String[] args)                  // unit testing of the methods (optional) 
	{
		KdTree t = new KdTree();
		
		t.insert(new Point2D(2,2));
		t.insert(new Point2D(0,0));
		t.insert(new Point2D(3,4));
		t.insert(new Point2D(2,4));
		t.insert(new Point2D(3,3));
		
		System.out.println(t.contains(new Point2D(2,3)));
		System.out.println(t.contains(new Point2D(0,0)));
		System.out.println(t.contains(new Point2D(3,4)));
		System.out.println(t.contains(new Point2D(2,4)));
		System.out.println(t.contains(new Point2D(3,3)));
		System.out.println(t.size());
		t.draw();
	}	



	private static class Node implements Comparable<Node>  {
		private Point2D p;      // the point
		private RectHV rect;    // the axis-aligned rectangle corresponding to this node
		private Node left;        // the left/bottom subtree
		private Node right;        // the right/top subtree
		private boolean hori;

		Node(Point2D pp, RectHV rectt, Node lbb, Node rtt, boolean horiz) {
			p = pp;
			rect = rectt;
			left = lbb;
			right = rtt;
			hori = horiz;
		}
		
		private String Data() {
			String s;
			s = " Point =  " + this.p.toString();
//			s += " Rect = " + rect.toString();
			s += " hori = " + hori;
			return s;
		}


		@Override
		public int compareTo(Node that) {
			/* YOUR CODE HERE */
			if (this.p.x() == that.p.x() && this.p.y() == that.p.y()) {
				return 0;
			}

			if (Double.compare(this.p.y(), that.p.y()) < 0)
				return -1;
			if (Double.compare(this.p.y(), that.p.y()) > 0)
				return 1;
			if (Double.compare(this.p.x(), that.p.x()) < 0)
				return -1;
			if (Double.compare(this.p.x(), that.p.x()) > 0)
				return 1;
			return 0;
		}
	}
}
