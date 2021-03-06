package sandbox.ik;

import sandbox.utils.DisplayPanel;
import sandbox.utils.NoiseGenerator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Point;

public class InverseKinematicsDemo extends DisplayPanel {
	public static final int WIDTH = 800, HEIGHT = 800;
	public static final int NUM_TIPS_PER_SIDE = 150;
	public static final int NUM_SEGMENTS_PER_TIP = 100;
	public static final int LENGHT_PER_SEGMENT = 9;
	private Segment[] tips;
	
	private boolean doonce = true;
	
	public float x = WIDTH / 2, y = HEIGHT / 2;
	private float angle = 0.f;
	
	public InverseKinematicsDemo() {
		super("InverseKinematicsDemo", WIDTH, HEIGHT);
		setClearColor(new Color(1.f, 1.f, 1.f, 1.f));
	}
	
	public void init() {
		tips = new Segment[NUM_TIPS_PER_SIDE * 4]; // 4 = number of sides of a rectangle;
		//top
		for (int i = 0; i < NUM_TIPS_PER_SIDE; ++i) {
			Segment pointer = null;
			for (int j = 0; j < NUM_SEGMENTS_PER_TIP; ++j) {
				if (pointer == null) {
					float xpos = i * (float)WIDTH / NUM_TIPS_PER_SIDE;
					pointer = new Segment(xpos, 0, LENGHT_PER_SEGMENT, 3 * (float)Math.PI / 2);
					continue;
				}
				Segment s = new Segment(pointer, LENGHT_PER_SEGMENT, 3 * (float)Math.PI / 2);
				pointer = s;
			}
			tips[i] = pointer;
		}
		
		//bottom
		for (int i = 0; i < NUM_TIPS_PER_SIDE; ++i) {
			Segment pointer = null;
			for (int j = 0; j < NUM_SEGMENTS_PER_TIP; ++j) {
				if (pointer == null) {
					float xpos = i * (float)WIDTH / NUM_TIPS_PER_SIDE;
					pointer = new Segment(xpos, HEIGHT, LENGHT_PER_SEGMENT, (float)Math.PI / 2);
					continue;
				}
				Segment s = new Segment(pointer, LENGHT_PER_SEGMENT, (float)Math.PI / 2);
				pointer = s;
			}
			tips[i + NUM_TIPS_PER_SIDE] = pointer;
		}
		
		//left
		for (int i = 0; i < NUM_TIPS_PER_SIDE; ++i) {
			Segment pointer = null;
			for (int j = 0; j < NUM_SEGMENTS_PER_TIP; ++j) {
				if (pointer == null) {
					float ypos = i * (float)HEIGHT / NUM_TIPS_PER_SIDE;
					pointer = new Segment(0, ypos, LENGHT_PER_SEGMENT, (float)Math.PI);
					continue;
				}
				Segment s = new Segment(pointer, LENGHT_PER_SEGMENT, (float)Math.PI);
				pointer = s;
			}
			tips[i + NUM_TIPS_PER_SIDE * 2] = pointer;
		}
		
		
		
		//right
		for (int i = 0; i < NUM_TIPS_PER_SIDE; ++i) {
			Segment pointer = null;
			for (int j = 0; j < NUM_SEGMENTS_PER_TIP; ++j) {
				if (pointer == null) {
					float ypos = i * (float)HEIGHT / NUM_TIPS_PER_SIDE;
					pointer = new Segment(WIDTH, ypos, LENGHT_PER_SEGMENT, 0.f);
					continue;
				}
				Segment s = new Segment(pointer, LENGHT_PER_SEGMENT, 0.f);
				pointer = s;
			}
			tips[i + NUM_TIPS_PER_SIDE * 3] = pointer;
		}
	}
	
	
	public void update(float dt) {
		
		Point a = this.getMousePosition();
		if (a != null) {
			x = (float)a.x;
			y = (float)a.y;
		}
		//angle += dt * 0.5f;
		//float dx = (float)NoiseGenerator.noise((float)Math.cos(angle),(float)Math.sin(angle)) * 2 - 1;
		//float dy = (float)NoiseGenerator.noise((float)Math.cos(-angle) * 1.5f,(float)Math.sin(-angle) * 1.5f) * 2 - 1;
		
		for (Segment tip : tips) {
			tip.anchoredFollow(x , y );
		}
		
	}
	
	public void render(Graphics2D g) {
		//if (doonce) {
		//	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//}
		
		g.setColor(Color.black);
		
		for (Segment tip : tips) {
			Segment current = tip;
			float color = 0.0f;
			while (current != null) {
				g.setColor(new Color(color, color, color, 1.f));
				current.draw(g);
				current = current.getParent();
				color += 0.01;
			}
		}
		
	}
	
	public static void main(String[] args) {
		new InverseKinematicsDemo().generateJFrame();
	}
}