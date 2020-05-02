package sandbox.convexhull;

import java.awt.Point;

public class Triangle {
	public Point v1;
	public Point v2;
	public Point v3;
	
	public Triangle(Point v1, Point v2, Point v3) {
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;
	}
	
	public int[] getVertexX() {
		int[] result = { v1.x, v2.x, v3.x };
		
		return result;
	}
	
	public int[] getVertexY() {
		int[] result = { v1.y, v2.y, v3.y };
		
		return result;
	}
}