package sandbox;

import sandbox.utils.Particle;
import sandbox.utils.DisplayPanel;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;

public class Boids extends DisplayPanel{
	public static int PANEL_WIDTH = 900;
	public static int PANEL_HEIGHT = 900;
	public static int BOIDS_NUM = 400;
	public static int BOIDS_RAD = 5;
	
	public ArrayList<Particle> boids = new ArrayList<Particle>();
	public Boids() {
		super("Boids are cool", PANEL_WIDTH, PANEL_HEIGHT);
	}
	
	public void init() {
		for (int i = 0; i < BOIDS_NUM; ++i) {
			float x = 450;
			float y = 450;
			Particle newBoid = new Particle(x, y);
			newBoid.vx = (float)Math.random() * 100 - 50;
			newBoid.vy = (float)Math.random() * 100 - 50;
			boids.add(newBoid);
		}
		
		setClearColor(Color.black);
	}
	
	
	public void update(float dt) {
		for (Particle boid : boids) {
			boid.x += boid.vx * dt;
			boid.y += boid.vy * dt;
			
			float steerx = 0.f;
			float steery = 0.f;
			float posx = 0.f;
			float posy = 0.f;
			float avoidx = 0.f;
			float avoidy = 0.f;
			int num = 0;
			for (Particle other : boids) {
				if (other == boid) continue;
				float dx = Math.abs(other.x - boid.x);
				float dy = Math.abs(other.y - boid.y);
				if (dx > 450) dx = 900 - dx;
				if (dy > 450) dy = 900 - dy;
				if (other.x < boid.x) dx = -dx;
				if (other.y < boid.y) dy = -dy;
				float dist = (float)Math.sqrt(dx * dx + dy * dy);
				if (dist < 300.f) {
					num++;
					steerx += other.vx;
					steery += other.vy;
					posx += dx;
					posy += dy;
					
				}
				
					avoidx += -dx * (150.f / (dist * dist));
					avoidy += -dy * (150.f / (dist * dist));
			}
			
			
			steerx += avoidx;
			steery += avoidy;
			
			float diffx = steerx - boid.vx;
			float diffy = steery - boid.vy;
			float difflen = (float)Math.sqrt(diffx * diffx + diffy * diffy);
			diffx *= 200.f / difflen;
			diffy *= 200.f / difflen;
			boid.vx += diffx * dt;
			boid.vy += diffy * dt;
			
			diffx = posx - boid.vx;
			diffy = posy - boid.vy;
			difflen = (float)Math.sqrt(diffx * diffx + diffy * diffy);
			diffx *= 200.f / difflen;
			diffy *= 200.f / difflen;
			boid.vx += diffx * dt;
			boid.vy += diffy * dt;
			
			diffx = avoidx - boid.vx;
			diffy = avoidy - boid.vy;
			difflen = (float)Math.sqrt(diffx * diffx + diffy * diffy);
			diffx *= 200.f / difflen;
			diffy *= 200.f / difflen;
			boid.vx += diffx * dt;
			boid.vy += diffy * dt;
			
			float len = (float)Math.sqrt(boid.vx * boid.vx + boid.vy * boid.vy);
			if (len > 200) {
				boid.vx *= 200.f / len;
				boid.vy *= 200.f / len;
			}
			
			if (boid.x > PANEL_WIDTH) boid.x = 0;
			if (boid.x < 0) boid.x = PANEL_WIDTH;
			if (boid.y > PANEL_HEIGHT) boid.y = 0;
			if (boid.y < 0) boid.y = PANEL_HEIGHT;
			
			
		}
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.white);
		for (Particle boid : boids) {
			g.fillOval((int)boid.x - BOIDS_RAD, (int)boid.y - BOIDS_RAD, BOIDS_RAD * 2, BOIDS_RAD * 2);
		}
	}
	
	public static void main(String[] args) {
		new Boids().generateJFrame();
	}
}