package sandbox;

import sandbox.utils.DisplayPanel;
import sandbox.utils.NoiseGenerator;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class NoiseTest extends DisplayPanel {

	public NoiseTest() {
		super("Noise Test", 800, 800);
	}
	
	private boolean doonce = true;
	private NoiseMaker nmaker1 = new NoiseMaker(400, 400,  0.f, 0.f, 10.f, 10.f);
	private NoiseMaker nmaker2 = new NoiseMaker(400, 400, 10.f, 0.f, 20.f, 10.f);
	private NoiseMaker nmaker3 = new NoiseMaker(400, 400,  0.f,10.f, 10.f, 20.f);
	private NoiseMaker nmaker4 = new NoiseMaker(400, 400, 10.f,10.f, 20.f, 20.f);
	
	public void init() {
		Thread t1 = new Thread(nmaker1);
		Thread t2 = new Thread(nmaker2);
		Thread t3 = new Thread(nmaker3);
		Thread t4 = new Thread(nmaker4);
		t1.start();
		t2.start();
		t3.start();
		t4.start();
	}
	
	public void render(Graphics2D g) {
		g.drawImage(nmaker1.img, 0  ,0  ,400,400, null);
		g.drawImage(nmaker2.img, 400,0  ,400,400, null);
		g.drawImage(nmaker3.img, 0  ,400,400,400, null);
		g.drawImage(nmaker4.img, 400,400,400,400, null);
	}
	
	public void update(float dt) {
	}

	public static void main(String[] args) {
		new NoiseTest().generateJFrame();
	}
	
	public class NoiseMaker implements Runnable {
		public BufferedImage img;
		private float sx, sy;
		private float scalex, scaley;
		public int progress = 0;
		public boolean ready = false;
		
		public NoiseMaker(int w, int h, float sx, float sy, float ex, float ey) {
			img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			this.sx = sx;
			this.sy = sy;
			this.scalex = (ex - sx) / w;
			this.scaley = (ey - sy) / h;
			
			System.out.println(scalex + " , " + scaley);
		}
		
		public void run() {
			Graphics g = img.getGraphics();
			g.setColor(Color.black);
			g.fillRect(0,0,img.getWidth(),img.getHeight());
			for (int i = 0; i < img.getWidth(); ++i) {
				for (int j = 0; j < img.getHeight(); ++j) {
					float noise = (float)NoiseGenerator.noise2D(i * scalex + sx, j * scaley + sy);
					noise = noise * 0.5f + 0.5f;
					//g.setColor(new Color(noise, noise, noise, 1.f));
					//g.fillRect(i, j, 1, 1);
					if (noise > 0.3f && noise < 0.31f) {
						float a = (0.31f - noise) / 0.2f  + 0.5f;
						g.setColor(new Color(0.1f, 0.1f, 0.f, a));
						g.fillRect(i, j, 1, 1);
					}
					
					if (noise > 0.4f && noise < 0.41f) {
						float a = (0.41f - noise) / 0.2f  + 0.5f;
						g.setColor(new Color(0.3f, 0.3f, 0.f, a));
						g.fillRect(i, j, 1, 1);
					}
					
					if (noise > 0.5f && noise < 0.51f) {
						float a = (0.51f - noise) / 0.2f  + 0.5f;
						g.setColor(new Color(0.7f, 0.7f, 0.f, a));
						g.fillRect(i, j, 1, 1);
					}
					
					if (noise > 0.6f && noise < 0.61f) {
						float a = (0.61f - noise) / 0.2f  + 0.5f;
						g.setColor(new Color(1.f, 1.f, 0.f, a));
						g.fillRect(i, j, 1, 1);
					}
					
					if (noise > 0.7f && noise < 0.71f) {
						float a = (0.71f - noise) / 0.2f  + 0.5f;
						g.setColor(new Color(1.f, 1.f, 0.8f, a));
						g.fillRect(i, j, 1, 1);
					}
					
					progress++;
				}
			}
			ready = true;
		}
	}
}