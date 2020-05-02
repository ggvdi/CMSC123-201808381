package sandbox.convexhull;

import sandbox.utils.DisplayPanel;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Arrays;

public class ConvexHullDemo extends DisplayPanel {
	private ArrayList<Point> points = new ArrayList<Point>();
	private ArrayList<Point> wrapper = null;
	
	public static final int POINT_RADIUS = 5,
							PANEL_WIDHT  = 800,
							PANEL_HEIGHT = 800;
	public static final long serialVersionUID = 0;
	
	public ConvexHullDemo() {
		super("Convex Hull with Jarvis March Algorithm", PANEL_WIDHT, PANEL_HEIGHT);
		
		addMouseListener( new MouseAdapter() {		
			public void mouseClicked(MouseEvent e) { 
				if (e.getButton() == MouseEvent.BUTTON1) { 
					Point a = ConvexHullDemo.this.getMousePosition();
					if (a != null) {
						if (a.x > PANEL_WIDHT) a.x = PANEL_WIDHT;
						if (a.y > PANEL_HEIGHT) a.y = PANEL_HEIGHT;
						if (a.x < 0) a.x = 0;
						if (a.y < 0) a.y = 0;
						points.add(a);
						if (points != null  && points.size() > 2)
							wrapper = JarvisMarch.solve((ArrayList<Point>)(points.clone()));
					}
				} 
			}  
			public void mouseEntered(MouseEvent e) {}  
			public void mouseExited(MouseEvent e) {}  
			public void mousePressed(MouseEvent e) {} 
			public void mouseReleased(MouseEvent e) {}
		});
	}
	
	public void init() {
	}
	
	public void update(float dt) {
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.black);
		for (Point p : points) {
			g.fillOval(p.x - POINT_RADIUS, p.y - POINT_RADIUS, POINT_RADIUS * 2, POINT_RADIUS * 2);
		}
		
		if (wrapper != null && wrapper.size() > 0) {
			int numVertices = wrapper.size();
			int[] xVertices = new int[numVertices];
			int[] yVertices = new int[numVertices];
			
			for (int i = 0; i < numVertices; ++i) {
				xVertices[i] = wrapper.get(i).x;
				yVertices[i] = wrapper.get(i).y;
			}
			
			g.setColor(new Color(0.f, 1.f, 1.f, 0.2f));
			g.fillPolygon(xVertices, yVertices, numVertices);
		}
	}
	
	public static void main(String[] args) {
		new ConvexHullDemo().generateJFrame();
	}
}
	