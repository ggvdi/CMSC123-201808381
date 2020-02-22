import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Image;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class FireworksPanel extends JPanel implements Runnable{
	public static int FIREWORK_WIDTH = 10;
	public static int CHARGE_WIDTH = 3;
	
	public Thread mainThread;
	
	private Image dbImage = null;
	private Graphics dbg;
	
	private BufferedInput bufferedInput = null;
	
	private ArrayList<Particle> tbaParticles = new ArrayList<Particle>();
	private ArrayList<Particle> activeParticles = new ArrayList<Particle>();
	private ArrayList<Particle> deadParticles = new ArrayList<Particle>();
	
	public void render(Graphics G) {
		for (Particle p : activeParticles) {
			G.setColor(new Color(p.r, p.g, p.b));
			if (p.charge)
				G.fillOval(p.X(), p.Y(), CHARGE_WIDTH, CHARGE_WIDTH); 
			else 
				G.fillOval(p.X(), p.Y(), FIREWORK_WIDTH, FIREWORK_WIDTH); 
		}
	}
	
	public void update() {
		activeParticles.addAll(tbaParticles);
		activeParticles.removeAll(deadParticles);
		tbaParticles.clear();
		for (Particle p : activeParticles) {
			p.update();
			if (p.charge) {
				if (p.isDead()) deadParticles.add(p);
				p.v_x *= 0.96;
				p.v_y *= 0.96;
			}
			
			if (!p.charge) {
				if (p.v_y >= 0) {
					deadParticles.add(p);
					for (int i = 0; i < 50; ++i) {
						Particle tba = new Particle(p.X(), p.Y(), true, p.r, p.g, p.b);
						tba.setV((float)Math.random() * 5.2f - 2.7f, (float)Math.random() * 5.2f - 2.7f);
						tbaParticles.add(tba);
					}
				}
			}
		}
	}
	
	private void doubleBuffering(){
		if (dbImage == null){
			dbImage = createImage(800, 800);
			if (dbImage == null){
				System.out.println("ERROR: Double Buffering Image is null!");
				return;
			}
			dbg = dbImage.getGraphics();
		}
		dbg.setColor(new Color( 0, 0, 0, 70));
		dbg.fillRect(0,0,800, 800);
		if (dbg!=null)
			render(dbg);
	}
	
	private void paintPanel(){
		Graphics g;
		try{
			g = this.getGraphics();
			g.setColor(Color.white);
			if ( (g!=null)  && dbImage != null){
				g.drawImage(dbImage, 0,0, 800, 800, null);
			}
			Toolkit.getDefaultToolkit().sync();
			g.dispose();
		}
		catch (Exception e)
		{ System.out.println("Graphics context error: " + e); }
	}
	
	public void input() {
		if (bufferedInput == null) return;
		
		Particle tba = new Particle(bufferedInput.x_pos, 800);
		tba.setV(0, -((float)Math.random() * 3.2f + 10.2f));
		tbaParticles.add(tba);
		
		
		bufferedInput = null;
	}
	public FireworksPanel() {
		
		addMouseListener( new MouseAdapter() {		
			public void mouseClicked(MouseEvent e) { 
				if (e.getButton() == MouseEvent.BUTTON1)
					bufferedInput = new BufferedInput(false, e.getX(), e.getY());
				else if (e.getButton() == MouseEvent.BUTTON3)
					bufferedInput = new BufferedInput(true , e.getX(), e.getY());
			}  
			public void mouseEntered(MouseEvent e) {}  
			public void mouseExited(MouseEvent e) {}  
			public void mousePressed(MouseEvent e) {} 
			public void mouseReleased(MouseEvent e) {}
		});
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				update();
				input();
				doubleBuffering();
				paintPanel();
				Thread.sleep(16);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void addNotify() {
		super.addNotify();
		System.out.println("FireworksPanel starts Thread");
		mainThread = new Thread(this);
		mainThread.start();
	}
	
	public static void main(String[] args) {
		JFrame f = new JFrame("Fireworks");
		FireworksPanel main = new FireworksPanel();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setPreferredSize(new Dimension(900, 900));
		f.setSize(800, 800);
		f.pack();
		f.setVisible(true);
		System.out.println("ADDING PANELS");
		f.add(main);
		
	}
	
	private class BufferedInput {
		public int x_pos, y_pos;
		public boolean mouse1;
		
		public BufferedInput(boolean mouse1, int x_pos, int y_pos) {
			this.mouse1 = mouse1;
			this.x_pos = x_pos;
			this.y_pos = y_pos;
		}
	}
	
	private class Particle {
		public float x, y,
				   v_x, v_y,
				   a_x, a_y,
				   r,g,b;
		public int lifespan = 55;
		public boolean charge;
		public static final float G = -0.15f;
				   
				   
		public Particle(int x, int y) { this(x,y, false); }
		public Particle(int x, int y, boolean charge) { this(x,y,charge, (float)Math.random(), (float)Math.random(), (float)Math.random()); }
		public Particle(int x, int y, boolean charge, float r, float g, float b) {
			this.x   = x;   this.y   = y;
			this.v_x = 0.f; this.v_y = 0.f;
			this.a_x = 0.f; this.a_y = 0.f;
			this.charge = charge;
			this.r = r;
			this.g = g;
			this.b = b;
		}
		
		public void setV(float x, float y) {
			this.v_x = x; this.v_y = y;
		}
		
		public boolean isDead() { return lifespan < 0; }
		
		public void update() {
			lifespan--;
			this.x += v_x;
			this.y += v_y;
			this.v_x += a_x;
			this.v_y += a_y;
			this.a_x = 0.f;
			this.a_y = 0.f;
			this.v_y -= G;
		}
		
		public void addForce(float x, float y) {
			this.a_x += x;
			this.a_y += y;
		}
		
		public int X() { return (int)x; }
		public int Y() { return (int)y; }
	}
}
