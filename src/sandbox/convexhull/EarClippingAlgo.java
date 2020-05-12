package sandbox.convexhull;

import java.util.ArrayList;
import java.awt.Point;

public class EarClippingAlgo {
	public static ArrayList<Triangle> solve(ArrayList<Point> allPoints, ArrayList<Point> points, int start) {
		ArrayList<Triangle> result = new ArrayList<Triangle>();
		int i = start;
		while (true) {
			if (points.size() < 3) return null;
			
			int v1Index = mod(i, points.size(), -1);
			Point v1 = points.get(v1Index);
			Point v2 = points.get(mod(i, points.size(), 0));
			Point v3 = points.get(mod(i, points.size(), 1));
			
			System.out.println("CHECKING " + v2);
			System.out.println("CROSS " + cross(v1, v2, v3));
			if (cross(v1, v2, v3) > 0) {
				//v2 is convex
				boolean isEar = true;
				for (Point p : allPoints) {
					//check if any point is inside the triangle
					if (inside(v1, v2, p) &&
						inside(v2, v3, p) &&
						inside(v1, v3, p)) {
							isEar = false;
							break;
						}
				}
				
				if (isEar) {
					System.out.println("IS_EAR " + v2 );
					//remove from list 
					result.add(new Triangle(v1, v2, v3));

					points.remove(v2);
					
					if (points.size() > 2) {
						ArrayList<Triangle> pass = solve(allPoints, points, i == 0 ? points.size() - 1 : v1Index);
						if (pass != null) result.addAll(pass);
					}
					return result;
				}
			}
			
			i = mod(i, points.size(), 1);
		}
	}
	
	
	public static int cross(Point a, Point b, Point c) {
		int dx_ab = b.x - a.x;
		int dy_ab = b.y - a.y;
		int dx_ac = c.x - a.x;
		int dy_ac = c.y - a.y;
		
		return dy_ac * dx_ab - dx_ac * dy_ab;
	}
	
	public static boolean inside(Point v1, Point v2, Point check) {
		float dx = (float)(v2.x - v1.x);
		float dy = (float)(v2.y - v1.y);
		
		float proj1 = proj(v1, dx, dy);
		float proj2 = proj(v2, dx, dy);
		float projcheck= proj(check, dx, dy);
		
		float min = proj1 < proj2 ? proj1 : proj2;
		float max = proj1 > proj2 ? proj1 : proj2;
		
		return projcheck > min && projcheck < max;
	}
	
	public static float proj(Point p, float dx, float dy) {
		return p.x * dx + p.y * dy;
	}
	
	public static int mod(int ix, int range, int mod) {
		return (ix + range + mod) % range;
	}
}