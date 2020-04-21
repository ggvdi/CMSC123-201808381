package sandbox;

import java.awt.Graphics2D;
import java.awt.Color;
import java.util.Arrays;

import sandbox.utils.DisplayPanel;

public class Test extends DisplayPanel{
	
	private String fpss = "";
	
	private float  x,  y;
	private float tx, ty;
	private float vx, vy;
	private float ax, ay;
	
	private float  x2,  y2;
	private float vx2, vy2;
	private float ax2, ay2;
	
	public Test(int width, int height) {
		super("TEST", width, height);
		ax = 0; ay = 0;
		vx = 0; vy = -200;
		ax2 = 0; ay2 = 0;
		vx2 = 0; vy2 = 200;
		
		x = width  / 4.f;
		y = height / 2.f;
		x2 = width * 3.f / 4.f;
		y2 = height / 2.f;
		
		tx = width  / 2.f;
		ty = height / 2.f;
	}

	public void init() {
		setClearColor(new Color(1.f, 1.f, 1.f, 0.03f));
	}
	
	public void update(float dt) {
		float fps = 1.f / dt;
		int fpsi = (int)fps;
		fpss = ("FPS: " + fpsi);
		
		
		ax2 = x - x2;
		ay2 = y - y2;
		ax 	= x2 - x;
		ay  = y2 - y;
		
		vx += ax * dt;
		vy += ay * dt;
		
		x += vx * dt;
		y += vy * dt;
		
		vx2 += ax2 * dt;
		vy2 += ay2 * dt;
		
		x2 += vx2 * dt;
		y2 += vy2 * dt;
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.black);
		g.drawString(fpss, 0, getHeight());
		g.setColor(Color.blue);
		g.fillOval((int)x - 10,(int)y - 10, 20, 20);
		g.setColor(Color.green);
		g.fillOval((int)x2 - 10,(int)y2 - 10, 20, 20);
		g.setColor(new Color(1.f, 0.f, 0.f, 0.01f));
		g.fillOval((int)tx - 40,(int)ty - 40, 80, 80);
	}
	
	
	public static void main(String[] args) {
		System.out.println("ARGS: " + Arrays.toString(args));
		Test t = new Test(800, 800);
		t.generateJFrame();
	}
}