package sandbox;

import sandbox.utils.DisplayPanel;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class Wave extends DisplayPanel {
	private ArrayList<Point> points = new ArrayList<Point>();
	private ArrayList<Point> wrapper = null;
	
	public static final int PANEL_WIDTH  = 800,
							PANEL_HEIGHT = 800;
	public static final long serialVersionUID = 0;
	public float[][] current  = new float[PANEL_WIDTH][PANEL_HEIGHT];
	public float[][] previous = new float[PANEL_WIDTH][PANEL_HEIGHT];
	public float damping = 0.015f;
	
	
	public Wave() {
		super("Raindrop go drip drop", PANEL_WIDTH, PANEL_HEIGHT);
		
		for (int i = 0; i < PANEL_WIDTH; ++i) {
			for (int j = 0; j < PANEL_HEIGHT; ++j) {
				current[i][j] = 0.f;
				previous[i][j] = 0.f;
			}
		}
		
		addMouseListener( new MouseAdapter() {		
			public void mouseClicked(MouseEvent e) { 
				if (e.getButton() == MouseEvent.BUTTON1) { 
					Point a = Wave.this.getMousePosition();
					if (a != null) {
						current[a.x][a.y] = 1.f;
					}
				} 
			}  
			public void mouseEntered(MouseEvent e) {}  
			public void mouseExited(MouseEvent e) {}  
			public void mousePressed(MouseEvent e) {} 
			public void mouseReleased(MouseEvent e) {}
		});
		
		this.setClearColor(Color.black);
	}
	
	public void init() {
	}
	
	private boolean skip = false;
	public void update(float dt) {
		skip = !skip;
		
		if (skip) return;
		int x = (int)(Math.random() * 800.f);
		int y = (int)(Math.random() * 800.f);
		
		current[x][y] = 1.f;
	}
	
	public void render(Graphics2D g) {
		BufferedImage img = new BufferedImage(PANEL_WIDTH, PANEL_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		
		float[][] temp = new float[PANEL_WIDTH][PANEL_HEIGHT];
		for (int i = 1; i < PANEL_WIDTH - 1; ++i) {
			for (int j = 1; j < PANEL_HEIGHT - 1; ++j) {
				float val = 0;
				val += current[i - 1][j    ];
				val += current[i    ][j - 1];
				val += current[i + 1][j    ];
				val += current[i    ][j + 1];
				val /= 2;
				val -= previous[i][j];
				val *= 1 / (1 + damping);
				float colorVal = val;
				if (colorVal > 1.f) colorVal = 1.f;
				if (colorVal < 0.f) colorVal = 0.f;
				Color newcolor = new Color(colorVal, colorVal, colorVal); 
				img.setRGB(i, j, newcolor.getRGB());
				temp[i][j] = val;
			}
		}
		previous = current;
		current = temp;
		g.drawImage(img, 0, 0, null);
				
	}
	
	public static void main(String[] args) {
		new Wave().generateJFrame();
	}
}
	