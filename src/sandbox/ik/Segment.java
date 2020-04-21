package sandbox.ik;

import java.awt.Graphics2D;
import sandbox.utils.Vector2f;

public class Segment {
	private Segment parent = null;
	
	private float x,y,len,angle;
	
	public Segment(Segment parent, float len, float angle) {
		this(parent.getXEndPoint(), parent.getYEndPoint(), len, angle);
		this.parent = parent;
	}
	
	public Segment(float x, float y, float len, float angle) {
		this.x = x;
		this.y = y;
		this.len = len;
		this.angle = angle;
	}
	
	public float getAngle() {
		return angle;
	}
	
	public float getLength() {
		return len;
	}
	
	public void setLength(float len) {	
		this.len = len;
	}
	
	public void setAngle(float angle) {
		this.angle = angle;
	}
	
	public float getXEndPoint() {
		return len * (float)Math.cos(getCurrentAngle());
	}
	
	public float getYEndPoint() {
		return len * (float)Math.sin(getCurrentAngle());
	}
	
	public float getXStart() {
		if (parent != null) return parent.getXEndPoint() + parent.getXStart();
		return x;
	}
	
	public float getYStart() {
		if (parent != null) return parent.getYEndPoint() + parent.getYStart();
		return y;
	}
	
	public float getCurrentAngle() {
		//if (parent != null) return angle + parent.getCurrentAngle();
		return angle;
	}
	
	public Segment getParent() {
		return parent;
	}
	
	public void follow(float fx, float fy) {
		float dx = fx - x;
		float dy = fy - y;
		
		float mag = (float)Math.sqrt(dx * dx + dy * dy);
		dx = dx * len / mag;
		dy = dy * len / mag;
		
		float nAngle = (float)Math.atan2(dy, dx);
		
		this.x = fx - dx;
		this.y = fy - dy;
		this.angle = nAngle;
		
		if (parent != null) {
			parent.follow(x, y);
		}
	}
	
	
	public Vector2f anchoredFollow(float targetX, float targetY) {
		float dx = targetX - x;
		float dy = targetY - y;
		
		float mag = (float)Math.sqrt(dx * dx + dy * dy);
		dx = dx * len / mag;
		dy = dy * len / mag;
		
		float nAngle = (float)Math.atan2(dy, dx);
		
		
		this.angle = nAngle;
		
		
		Vector2f anchorOff = new Vector2f(0,0);
		
		if (parent == null)
			return new Vector2f(targetX - dx - x, targetY - dy - y);
		
		x = targetX - dx;
		y = targetY - dy;
		anchorOff = parent.anchoredFollow(x, y);
		x = x - anchorOff.x;
		y = y - anchorOff.y;
		
		return anchorOff;
	}
	
	public void draw(Graphics2D g) {
		
		//float xStart = getXStart();
		//float yStart = getYStart();
		
		float x2 = x + getXEndPoint();
		float y2 = y + getYEndPoint();

		g.drawLine((int)x, (int)y, (int)x2, (int)y2);
	}
}