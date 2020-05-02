package sandbox.convexhull;

import java.util.ArrayList;
import java.awt.Point;

public class JarvisMarch {
	public static ArrayList<Point> solve(ArrayList<Point> points) {
		ArrayList<Point> result = new ArrayList<Point>();
		
		//set leftmost point as starting point;
		Point currentVertex = points.get(0);
		for (Point p : points) {
			if (p.x < currentVertex.x) currentVertex = p;
		}
		
		Point start = currentVertex;
		
		//start with the leftmost point
		while (true) {
			result.add(currentVertex);
			
			Point nextVertex = points.get(0);
			if (nextVertex == currentVertex) nextVertex = points.get(1);
			
			for (Point p : points) {
				if (p == currentVertex) continue;
				int crossVal = cross(currentVertex, nextVertex, p);
				if (crossVal < 0) {
					nextVertex = p;
				}
				else if (crossVal == 0) {
					if (greater(currentVertex, nextVertex, p)) nextVertex = p;
				}
			}
			
			if (nextVertex == start) return result;
			currentVertex = nextVertex;
		}
	}
	
	
	public static boolean greater(Point a, Point b, Point c) {
		int dx_ab = b.x - a.x;
		int dy_ab = b.y - a.y;
		int dx_ac = c.x - a.x;
		int dy_ac = c.y - a.y;
		
		int dist_ab = dx_ab * dx_ab + dy_ab * dy_ab;
		int dist_ac = dx_ac * dx_ac + dy_ac * dy_ac;
		
		return dist_ab <= dist_ac;
	}
	//[0,0] [-1, 2], [-1, -2];
	//yc * xb - xc * yb
	//-2 * -1 - -1 * 2 = 2 +2 = 4
	//[0,0] [-1, -2], [1, -2];
	//yc * xb - xc * yb
	//-2 * -1 - 1 * -2 = 2 + 2 = 4
	private static int cross(Point a, Point b, Point c) {
		int dx_ab = b.x - a.x;
		int dy_ab = b.y - a.y;
		int dx_ac = c.x - a.x;
		int dy_ac = c.y - a.y;
		
		return dy_ac * dx_ab - dx_ac * dy_ab;
	}
}