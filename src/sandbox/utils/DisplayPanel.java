package sandbox.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.Image;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class DisplayPanel extends JPanel implements Runnable{
	public Thread mainThread;
	
	private Image dbImage = null;
	private Graphics2D dbg;
	private int width, height;
	private Color clearColor = Color.white;
	private String title;
	
	private long lastFrame = 0;
	private long deltaFrameTime = 0;
	private float targetFrameTime = 0.016f; // 60 FPS
	private JFrame FRAME = null;
	
	
	public DisplayPanel(String title, int width, int height) { 
		this.width = width;
		this.height = height;
		this.title = title;
	}
	
	public abstract void render(Graphics2D G);
	public abstract void update(float dt);
	public abstract void init();
	
	private void doubleBuffering(){
		if (dbImage == null){
			dbImage = createImage(width, height);
			if (dbImage == null){
				System.out.println("ERROR: Double Buffering Image is null!");
				return;
			}
			dbg = (Graphics2D)dbImage.getGraphics();
		}
		dbg.setColor(clearColor);
		dbg.fillRect(0,0,width, height);
		if (dbg!=null)
			render(dbg);
	}
	
	private void paintPanel(){
		Graphics g;
		try{
			g = this.getGraphics();
			g.setColor(Color.white);
			if ( (g!=null)  && dbImage != null){
				g.drawImage(dbImage, 0,0, width, height, null);
			}
			Toolkit.getDefaultToolkit().sync();
			g.dispose();
		}
		catch (Exception e)
		{ System.out.println("Graphics context error: " + e); }
	}
	
	
	@Override
	public void run() {
		init();
		lastFrame = System.nanoTime();
		float accumulator = 0.f;
		while(true) {
			try {
				while (accumulator >= targetFrameTime) {
					update(targetFrameTime);
					accumulator -= targetFrameTime;
				}
				doubleBuffering();
				paintPanel();
				
				long currentFrame = System.nanoTime();
				deltaFrameTime = currentFrame - lastFrame;
				lastFrame = currentFrame;
				long frameTimeMillis = deltaFrameTime / 1000000;
				float frameTimeSeconds = (float)frameTimeMillis / 1000.f;
				
				accumulator += frameTimeSeconds;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setClearColor(Color clearColor) {
		this.clearColor = clearColor;
	}
	
	@Override
	public void addNotify() {
		super.addNotify();
		mainThread = new Thread(this);
		mainThread.start();
	}
	
	public JFrame generateJFrame() {
		if (FRAME == null) {
			FRAME = new JFrame(title);
			FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			FRAME.setPreferredSize(new Dimension(900, 900));
			FRAME.setSize(width, height);
			FRAME.add(this);
			FRAME.pack();
			FRAME.setVisible(true);
		}
		
		return FRAME;
	}
}