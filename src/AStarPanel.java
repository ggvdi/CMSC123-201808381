import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Image;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AStarPanel extends JPanel implements Runnable{
	
	public static final int GRID_SIZE = 50;
	public static final int GRID_DIMENSIONS = 720;
	public static final int BLOCK_WIDTH = GRID_DIMENSIONS / GRID_SIZE,
							CIRCLE_WIDTH = BLOCK_WIDTH / 2,
							X_OFFSET = 10, Y_OFFSET = 10;
	public static final int START_X =  0, START_Y =  0,
							END_X   = 49, END_Y   = 49;
	
	private int[][] walls = new int[GRID_SIZE][GRID_SIZE];
	private	int[][] Fcosts = new int[GRID_SIZE][GRID_SIZE];
	private	int[][] Gcosts = new int[GRID_SIZE][GRID_SIZE];
	private Point[][] from = new Point[GRID_SIZE][GRID_SIZE];
	private ArrayList<Point> openSet;
	private ArrayList<Point> closedSet;
	private Point lineEnd;
	private boolean searchStarted = false;
	
	public Thread mainThread;
	
	private Image dbImage = null;
	private Graphics dbg;
	
	private BufferedInput bufferedInput = null;
	
	private double heuristic(int x, int y) {
		return Math.sqrt((double)((x - END_X)*(x - END_X)) + (double)((y - END_Y)*(y - END_Y)));
		//return Math.abs(x - END_X) + Math.abs(y - END_Y);
	}
	
	private double heuristic(Point p) {
		return heuristic(p.x, p.y);
	}
	
	private int AStar() {
		for (int i = 0; i < GRID_SIZE; ++i) {
			for (int j = 0; j < GRID_SIZE; ++j) {
				Fcosts[i][j] = Integer.MAX_VALUE;
				Gcosts[i][j] = Integer.MAX_VALUE;
			}
		}
		
		ArrayList<Point> openSet = new ArrayList<Point>();
		Gcosts[START_X][START_Y] = 0;
		Fcosts[START_X][START_Y] = 0;
		openSet.add(new Point(START_X, START_Y));
		
		while (!openSet.isEmpty()) {
			Point current = getLeast(openSet);
			
			if (current.x == END_X && current.y == END_Y) return 0;
			openSet.remove(current);
			
			for (Point p : getNeighbors(current)) {
				int tentativeCost = Gcosts[current.x][current.y] + walls[p.x][p.y];
				if (tentativeCost < Gcosts[p.x][p.y]) {
					from[p.x][p.y] = current;
					Gcosts[p.x][p.y] = tentativeCost;
					Fcosts[p.x][p.y] = Gcosts[p.x][p.y] + (int)heuristic(p);
					
					if (!openSet.contains(p)) openSet.add(p);
				}
			}
		}
		
		return -1;
	}
	
	private Point getLeast(ArrayList<Point> points) {
		int leastCost = Integer.MAX_VALUE;
		Point ret = null;
		for (Point p : points) {
			if (Fcosts[p.x][p.y] < leastCost) {
				leastCost = Fcosts[p.x][p.y];
				ret = p;
			}
		}
		return ret;
	}
	
	public void initAStar() {
		for (int i = 0; i < GRID_SIZE; ++i) {
			for (int j = 0; j < GRID_SIZE; ++j) {
				Fcosts[i][j] = Integer.MAX_VALUE;
				Gcosts[i][j] = Integer.MAX_VALUE;
			}
		}
			
		openSet = new ArrayList<Point>();
		closedSet = new ArrayList<Point>();
		
		Gcosts[START_X][START_Y] = 0;
		Fcosts[START_X][START_Y] = 0;
		openSet.add(new Point(START_X, START_Y));
	}
	
	public void AStarStep() {
		if (openSet.isEmpty()) {
			searchStarted = false;
			return;
		}
		
		Point current = getLeast(openSet);
		lineEnd = current;
			
		if (current.x == END_X && current.y == END_Y) {
			searchStarted = false;
			return;
		}
		openSet.remove(current);
		closedSet.add(current);
			
		for (Point p : getNeighbors(current)) {
			int tentativeCost = Gcosts[current.x][current.y] + walls[p.x][p.y];
			if (tentativeCost < Gcosts[p.x][p.y]) {
				from[p.x][p.y] = current;
				Gcosts[p.x][p.y] = tentativeCost;
				Fcosts[p.x][p.y] = Gcosts[p.x][p.y] + (int)heuristic(p);
				
				if (!openSet.contains(p)) openSet.add(p);
			}
		}
	}
	
	private ArrayList<Point> getNeighbors(Point p) {
		ArrayList<Point> ret = new ArrayList<Point>();
		//shut up
		if (p.x > 0             && walls[p.x-1][p.y  ] >= 0) ret.add(new Point(p.x - 1, p.y    ));
		if (p.y > 0             && walls[p.x  ][p.y-1] >= 0) ret.add(new Point(p.x    , p.y - 1));
		if (p.x < GRID_SIZE - 1 && walls[p.x+1][p.y  ] >= 0) ret.add(new Point(p.x + 1, p.y    ));
		if (p.y < GRID_SIZE - 1 && walls[p.x  ][p.y+1] >= 0) ret.add(new Point(p.x    , p.y + 1));
		
		return ret;
	}
	
	public void render(Graphics G) {
		G.setColor(new Color( 255, 0, 0));
		for (int i = 0; i < GRID_SIZE; ++i) {
			for (int j = 0; j < GRID_SIZE; ++j) {
				if (closedSet != null) {
					if (closedSet.contains(new Point(i,j))) G.setColor(Color.white);
					else if (openSet.contains(new Point(i,j))) G.setColor(Color.green);
					else G.setColor(Color.red);
				}
				if (walls[i][j] < 0)
					G.fillRect(X_OFFSET + i * BLOCK_WIDTH, Y_OFFSET + j * BLOCK_WIDTH, BLOCK_WIDTH, BLOCK_WIDTH);
				else 
					G.drawOval(X_OFFSET + i * BLOCK_WIDTH + CIRCLE_WIDTH / 2, Y_OFFSET + j * BLOCK_WIDTH + CIRCLE_WIDTH / 2, CIRCLE_WIDTH, CIRCLE_WIDTH);
				if (walls[i][j] > 0)
					G.drawString(String.valueOf(walls[i][j]), X_OFFSET + i * BLOCK_WIDTH + CIRCLE_WIDTH, Y_OFFSET + j * BLOCK_WIDTH + CIRCLE_WIDTH);
			}
		}
		
		G.setColor(new Color(0, 255, 0));
		
		if (lineEnd == null) return;
		
		Point a = lineEnd;
		Point b = from[lineEnd.x][lineEnd.y];
		while (b != null) {
			G.drawLine(X_OFFSET + a.x * BLOCK_WIDTH + BLOCK_WIDTH / 2, X_OFFSET + a.y * BLOCK_WIDTH + BLOCK_WIDTH / 2,
					   X_OFFSET + b.x * BLOCK_WIDTH + BLOCK_WIDTH / 2, X_OFFSET + b.y * BLOCK_WIDTH + BLOCK_WIDTH / 2);
			Point c = from[b.x][b.y];
			a = b;
			b = c;
		}
	}
	
	
	private void doubleBuffering(){
		if (dbImage == null){
			dbImage = createImage(GRID_DIMENSIONS + 2 * X_OFFSET, GRID_DIMENSIONS + 2 * Y_OFFSET);
			if (dbImage == null){
				System.out.println("ERROR: Double Buffering Image is null!");
				return;
			}
			dbg = dbImage.getGraphics();
		}
		dbg.setColor(new Color( 0, 0, 0));
		dbg.fillRect(0,0,GRID_DIMENSIONS + 2 * X_OFFSET, GRID_DIMENSIONS + 2 * Y_OFFSET);
		if (dbg!=null)
			render(dbg);
	}
	
	private void paintPanel(){
		Graphics g;
		try{
			g = this.getGraphics();
			g.setColor(Color.white);
			if ( (g!=null)  && dbImage != null){
				g.drawImage(dbImage, 0,0, GRID_DIMENSIONS + 2 * X_OFFSET, GRID_DIMENSIONS + 2 * Y_OFFSET, null);
			}
			Toolkit.getDefaultToolkit().sync();
			g.dispose();
		}
		catch (Exception e)
		{ System.out.println("Graphics context error: " + e); }
	}
	
	public void input() {
		if (bufferedInput == null) return;
		int x_pos = toGridCoordinates(false, bufferedInput.x_pos);
		int y_pos = toGridCoordinates(true , bufferedInput.y_pos);
		
		
		System.out.println(x_pos + "," + y_pos);
		if (x_pos < 0 || y_pos < 0 || x_pos >= GRID_SIZE || y_pos >= GRID_SIZE) {
			if (!searchStarted && !bufferedInput.mouse1) {
				initAStar();
				System.out.println("ADASD");
				searchStarted = true;
			}
			System.out.println("WTF" + (searchStarted ? "YES" : "NO"));
			bufferedInput = null;
			return;
		}
		
		if (!bufferedInput.mouse1) walls[x_pos][y_pos]++;
		else {
			walls[x_pos][y_pos]--;
			if (walls[x_pos][y_pos] < -1) walls[x_pos][y_pos] = -1;
		}
		
		bufferedInput = null;
	}
	
	public int toGridCoordinates(boolean y, int pos) {
		if (y && ( pos < Y_OFFSET || pos > Y_OFFSET + GRID_DIMENSIONS) ) return -1;
		else if ( pos < X_OFFSET || pos > X_OFFSET + GRID_DIMENSIONS) return -1;
		
		return (pos - ( y ? Y_OFFSET : X_OFFSET )) / BLOCK_WIDTH;
	}
	
	public AStarPanel() {
		
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
				if (searchStarted) AStarStep();
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
		System.out.println("AStarPanel starts Thread");
		this.AStar();
		mainThread = new Thread(this);
		mainThread.start();
	}
	
	public static void main(String[] args) {
		JFrame f = new JFrame("A* Implementation");
		AStarPanel main = new AStarPanel();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(800, 800);
		f.pack();
		f.setVisible(true);
		System.out.println("ADDING PANELS");
		f.add(main);
		
		//PriorityQueue<Point> a = new PriorityQueue<Point>();
		//a.add(new Point(0, 2));
		//if (a.contains(new Point(0, 2))) System.out.println("YAW");
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
}