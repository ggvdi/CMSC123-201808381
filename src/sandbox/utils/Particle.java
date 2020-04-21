package sandbox.utils;

public class Particle {
	public float  x,  y;
	public float vx, vy;
	public float ax, ay;
	public float mass;
	
	public Particle(float x, float y, float mass) {
		this.x = x;
		this.y = y;
		this.mass = mass;
		vx = 0; vy = 0;
		ax = 0; ay = 0;
	}
	
	public Particle(float x, float y) {
		this(x,y,1.f);
	}
	
}