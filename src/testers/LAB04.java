package testers;

import datastructures.Tree;
import datastructures.graph.*;

import java.util.ArrayList;
import java.util.Arrays;

public class LAB04 {
	
	public static int getIndexOf(String str, String[] arr) {
		for (int i = 0; i < arr.length; ++i) {
			if (str.equals(arr[i])) return i;
		}
		return -1;
	}
	
	
	public static int countNot(int not, int[] arr) {
		int count = 0;
		for (int i = 0; i < arr.length; ++i) {
			if ( arr[i] != not ) count++;
		}
		
		return count;
	}
	
	public static int[] generateInDegrees(DirectedGraph DG, String[] vertices) {
		int[] degrees = new int[vertices.length];
		for (String vertex : vertices) {
			String[] adj = DG.getAdjacentVertices(vertex);
			for (String str : adj) {
				degrees[getIndexOf(str, vertices)] += 1;
				//System.out.println(str + " + 1 ");
			}
		}
		
		return degrees;
	}
	
	public static int[] copyArray(int[] ar) {
		int[] n_ar = new int[ar.length];
		for (int i = 0; i < ar.length; ++i) {
			n_ar[i] = ar[i];
		}
		return n_ar;
	}
	
	public static ArrayList<String> getZeroes(String[] vertices, int[] degrees) {
		ArrayList<String> ret = new ArrayList<String>();
		for (int i = 0; i < vertices.length; ++i) {
			if (degrees[i] == 0) ret.add(vertices[i]);
			//System.out.println(vertices[i] + ", " + degrees[i]);
		}
		
		return ret;
	}
	
	public static int[] generateSubGraph(DirectedGraph g, String vertex, String[] vertices, int[] inDegrees) {
		int ix = getIndexOf(vertex, vertices);
		String[] adjacents = g.getAdjacentVertices(vertices[ix]);
		
		int[] n_inDegrees = copyArray(inDegrees);
		n_inDegrees[ix]--;
		
		for (String str : adjacents) {
			int v_ix = getIndexOf(str, vertices);
			n_inDegrees[v_ix]--;
		}
		
		return n_inDegrees;
	}
	
	public static void generatePaths(DirectedGraph g, Tree<String> path, String[] vertices, int[] inDegrees) throws InputNotDAGException{
		
		int[] subGraphDegrees = generateSubGraph(g, path.getData(), vertices, inDegrees);
		ArrayList<String> zeroes = getZeroes(vertices, subGraphDegrees);
		
		if (countNot(-1, subGraphDegrees) == 0) return;
		if (zeroes.size() == 0) throw new InputNotDAGException();
		
		for (String zero : zeroes) {
			path.addChild(zero);
		}
		
		for (Tree<String> childPath : path.getChildren()) {
			generatePaths(g, childPath, vertices, subGraphDegrees);
		}
	}
	
	public static ArrayList<String[]> topologicalSort(DirectedGraph DG) throws InputNotDAGException {
		Tree<String> paths = new Tree<String>();
		String[] vertices = DG.getVertices();
		int[] inDegrees = generateInDegrees(DG, vertices);
		
		ArrayList<String> zeroes = getZeroes(vertices, inDegrees);
		if (zeroes.size() == 0) throw new InputNotDAGException();
		
		for (String str : zeroes) {
			paths.addChild(str);
		}
		
		for (Tree<String> zero : paths.getChildren()) {
			generatePaths(DG, zero, vertices, inDegrees);
		}
		
		ArrayList<String[]> collection = new ArrayList<String[]>();
		
		generateCollection(paths, new String[0], collection);
	
		return collection;
	}
	
	
	public static void depthFirstTraversal(Tree<String> tree) {
		depthFirstTraversal(tree, "");
	}
	
	public static void depthFirstTraversal(Tree<String> tree, String path) {
		if (tree.getChildren().size() == 0){
			System.out.println(path);
			return;
		}
		
		for (Tree<String> child : tree.getChildren()) {
			depthFirstTraversal(child, path +", "+ child.getData());
		}
	}
	
	public static void generateCollection(Tree<String> tree, String[] path, ArrayList<String[]> collection) {
		/////this takes so much memory
		String[] newpath = new String[path.length + 1];
		for (int i = 0; i < path.length; ++i) {
			newpath[i] = path[i];
		}
		newpath[path.length] = tree.getData();
		
		if (tree.getChildren().size() == 0){
			collection.add(newpath);
			return;
		}
		
		for (Tree<String> child : tree.getChildren()) {
			generateCollection(child, newpath, collection);
		}
	}
	
	
	/////EVERYTHING BELOW THIS LINE IS JUST THE TESTER
	public static void main(String[] args) {
		new LAB04().loop();
	}
	
	private java.util.Scanner scan = new java.util.Scanner(System.in);
	private DirectedGraph in = new DirectedGraph();
	
	public void loop() {
		while(true) {
			System.out.println("\nTESTER FOR LAB04 (TOPOLOGICAL SORTING)");
			System.out.println("\t1.Add Vertices");
			System.out.println("\t2.Add Edges");
			System.out.println("\t3.Show Topological Sort");
			System.out.println("\t4.Reset Graph");
			System.out.print(">");
			doChoice();
		}
	}
	
	public void doChoice() {
		int c = scan.nextInt();
		
		switch(c) {
			case 1:
				addVertices();
				break;
			case 2:
				addEdges();
				break;
			case 3:
				showTopologicalSort();
				break;
			case 4:
				in = new DirectedGraph();
				System.out.println("!!!!!!!!!!!!!!!!GRAPH RESET!!!!!!!!!!!!!!!!!");
				break;
			default :
				System.exit(1);
		}
	}
	
	public void addVertices() {
		System.out.print("NUMBER OF VERTICES TO ADD > ");
		int n = scan.nextInt();
		
		for (int i = 0; i < n; ++i) {
			System.out.print("ENTER NAME OF VERTEX > ");
			try {
				in.addVertex(scan.next());
			} catch (Exception e) {
				System.out.print(e.getMessage());
			}
		}
	}
	
	
	public void addEdges() {
		System.out.print("NUMBER OF EDGES TO ADD > ");
		int n = scan.nextInt();
		
		for (int i = 0; i < n; ++i) {
			System.out.print("FROM VERTEX > ");
			String a = scan.next();
			System.out.print("TO VERTEX > ");
			String b = scan.next();
			in.addEdge(a,b);
		}
	}
	
	public void showTopologicalSort() {
		ArrayList<String[]> sorts;
		
		try {
			sorts = topologicalSort(in);
			System.out.println("POSSIBLE TOPOLOGICAL SORTS : ");
		} catch (InputNotDAGException e) {
			System.out.println("ERROR : INPUT GRAPH IS NOT ACYCLIC");
			return;
		}
		
		for (String[] path : sorts) {
			System.out.print("[");
			for (String str : path) {
				if (str != null)
					System.out.print(str + " > ");
			}
			System.out.println("end]");
		}
	}
	
}

class InputNotDAGException extends Exception {
	public InputNotDAGException() {
		super();
	}
}