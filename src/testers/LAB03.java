package testers;

import datastructures.graph.*;
import java.util.Scanner;

public class LAB03 {
	
	private static int getIndex(String vertex, String[] array) {
		for (int i = 0; i < array.length; ++i) {
			if (vertex.equals(array[i])) return i;
		}
		return -1;
	}
	
	public static boolean isEmpty(boolean[] a) {
		for (int i = 0; i < a.length; ++i) {
			if (a[i]) return false;
		}
		return true;
	}
	
	public static int getLeast(boolean[] a, boolean[] c, int[] b) {
		int ret = -1;
		int least = Integer.MAX_VALUE;
		
		for (int i = 0; i  < a.length; ++i) {
			if (a[i] && !c[i] &&b[i] < least) {
				ret = i;
				least = b[i];
			}
		}
		
		return ret;
	}
	
	public static int[] SSUPL(Graph G, String startingVertex) {
		
		///SHHHHHHHHHHHHHHHHH
		String[] vertices = G.getVertices();
		boolean[] visited = new boolean[vertices.length];
		boolean[] openset = new boolean[vertices.length];
		int[] weights = new int[vertices.length];
		
		// set initial weights to +INFINITY
		for (int i = 0; i < weights.length; ++i) { weights[i] = Integer.MAX_VALUE; };
		
		int ix = getIndex(startingVertex, vertices);
		weights[ix] = 0;
		openset[ix] = true;
		int ixx; 
		
		while ((ixx = getLeast(openset, visited, weights)) != -1) {
			visited[ixx] = true;
			openset[ixx] = false;
			for (String s : G.getAdjacentVertices(vertices[ixx])) {
				int vix = getIndex(s, vertices);
				if (weights[vix] > weights[ixx] + 1)
						weights[vix] = weights[ixx] + 1;
				if (!visited[vix])
					openset[vix] = true;
			}
		}
		
		
		return weights;
	}
	
	public static Scanner scan = new Scanner(System.in);
	public static Graph g;
	
	public static void main(String[] args) {
		g = new UndirectedGraph();
		loop();
	}
	
	public static void loop() {
		while (true) {
		System.out.println("WELCOME TO SSUPL");
		System.out.println("\t1. Add Vertices");
		System.out.println("\t2. Add Edges");
		System.out.println("\t3. Print Weights");
		System.out.print("INPUT > ");
			int x = scan.nextInt();
			switch(x){
				case 1 :
					addVertex();
					break;
				case 2 :
					addEdge();
					break;
				case 3 :
					printWeight();
					break;
			}
		}
	}
	
	public static void addVertex() {
		System.out.print("ENTER NUMBER OF VERTICES TO ADD > ");
		int count = scan.nextInt();
		
		try {
			for (int i = 0; i < count; ++i) {
				System.out.print("ENTER NAME OF VERTEX TO ADD> "); 
				g.addVertex(scan.next());
			}	
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void addEdge() {
		System.out.println("EDGE FROM VERTEX > ");
		String a = scan.next();
		System.out.println("EDGE TO VERTEX > ");
		String b = scan.next();
		g.addEdge(a,b);
	}
	
	public static void printWeight() {
		System.out.println("ENTER STARTING VERTEX > ");
		String sv = scan.next();
		
		String[] v = g.getVertices();
		int[] w = SSUPL(g, sv);
		
		System.out.println("PATH LENGTHS FROM VERTEX \"" + sv + "\"");
		for (int  i = 0; i < v.length; ++i) {
			System.out.println("\t" + v[i] + " : " + w[i]);
		}
	}
}