package sandbox.convexhull;

import sandbox.utils.DisplayPanel;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Arrays;

public class EarClippingDemo extends DisplayPanel {
	private ArrayList<Point> points = new ArrayList<Point>();
	private ArrayList<Triangle> ears = null;
	
	public static final int POINT_RADIUS = 5,
							PANEL_WIDHT  = 800,
							PANEL_HEIGHT = 800;
	public static final long serialVersionUID = 0;
	
	public EarClippingDemo() {
		super("Ear Clipping Algorithm", PANEL_WIDHT, PANEL_HEIGHT);
		
		addMouseListener( new MouseAdapter() {		
			public void mouseClicked(MouseEvent e) { 
				if (e.getButton() == MouseEvent.BUTTON1) { 
					Point a = EarClippingDemo.this.getMousePosition();
					if (a != null) {
						if (a.x > PANEL_WIDHT) a.x = PANEL_WIDHT;
						if (a.y > PANEL_HEIGHT) a.y = PANEL_HEIGHT;
						if (a.x < 0) a.x = 0;
						if (a.y < 0) a.y = 0;
						points.add(a);
						
						ArrayList<Point> in  = new ArrayList<Point>();
						in.addAll(points);
						ArrayList<Point> in2  = new ArrayList<Point>();
						in2.addAll(points);
						ears = EarClippingAlgo.solve(in, in2, 0);
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
		
		if (ears == null) return;
		for (Triangle ear : ears) {
			int[] xcoords = ear.getVertexX();
			int[] ycoords = ear.getVertexY();
			
			g.setColor(new Color(0.f, 1.f, 1.f, 0.2f));
			g.fillPolygon(xcoords, ycoords, 3);
			g.setColor(Color.black);
			g.drawPolygon(xcoords, ycoords, 3);
		}
	}
	
	public static void main(String[] args) {
		new EarClippingDemo().generateJFrame();
	}
}
	