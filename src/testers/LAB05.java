package testers;

import datastructures.Tree;
import datastructures.Queue;
import datastructures.graph.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


/////////////////BASICALLY LAB04 BUT WITH MODIFICATIONS
public class LAB05 {
	
	private HashMap<String, Integer> weights = new HashMap<String, Integer>();
	
	public static int getIndexOf(String str, String[] arr) {
		for (int i = 0; i < arr.length; ++i) {
			if (str.equals(arr[i])) return i;
		}
		return -1;
	}
	
	public static int[] generateInDegrees(Graph g, String[] vertices) {
		int[] indegrees = new int[vertices.length];
		
		for (String t : vertices) {
			for (String v : g.getAdjacentVertices(t) ) {
				indegrees[getIndexOf(v, vertices)]++;
			}
		}
		
		return indegrees;
	}
	
	public static ArrayList<String> getZeroes(String[] vertices, int[] indegrees) {
		ArrayList<String> zeroes = new ArrayList<String>();
		for (int i = 0; i < vertices.length; ++i) {
			if (indegrees[i] == 0) zeroes.add(vertices[i]);
		}
		
		return zeroes;
	}
	
	public static WhyDontStructsExistInJava[] generateCriticalPaths(DirectedGraph g, String[] v, HashMap<String, Integer> weights) throws InputNotDAGException {
		String[] vertices = v;
		int[] indegrees = generateInDegrees(g, vertices);
		int[] finalweights = new int[vertices.length];
		String[] prev = new String[vertices.length];
		ArrayList<String> zeroes = getZeroes(vertices, indegrees);
		if (zeroes.size() == 0) throw new InputNotDAGException();
		for (String vertex : zeroes) {
			finalweights[getIndexOf(vertex, vertices)] = weights.containsKey(v) ? weights.get(v) : 1;
		}
		
		while(zeroes.size() > 0) {
		
			for (String vertex : zeroes) {
				int previx = getIndexOf(vertex, vertices);
				indegrees[previx]--;
				for (String adjacent : g.getAdjacentVertices(vertex)) {
					int ix = getIndexOf(adjacent, vertices);
					indegrees[ix]--;
					
					if (indegrees[ix] < 0) throw new InputNotDAGException();
					
					int weight = weights.containsKey(adjacent) ? weights.get(adjacent) : 1;
					
					if (prev[ix] == null) {
						finalweights[ix] = finalweights[previx] + weight;
						prev[ix] = vertex;
					}
					else {
						if (finalweights[ix] < finalweights[previx] + weight) {
							finalweights[ix] = finalweights[previx] + weight;
							prev[ix] = vertex;
						}
					}
				}
			}
			
			zeroes = getZeroes(vertices, indegrees);
		}
		
		WhyDontStructsExistInJava[] ret = new WhyDontStructsExistInJava[vertices.length];
	
		for (int i = 0; i < ret.length; ++i) {
			if (prev[i] == null)
				ret[i] = new WhyDontStructsExistInJava(-1, finalweights[i]);
			else 
				ret[i] = new WhyDontStructsExistInJava(getIndexOf(prev[i], vertices), finalweights[i]);
		}
	
		return ret;
	}
	
	
	
	/////EVERYTHING BELOW THIS LINE IS JUST THE TESTER
	public static void main(String[] args) {
		new LAB05().loop();
	}
	
	private java.util.Scanner scan = new java.util.Scanner(System.in);
	private DirectedGraph in = new DirectedGraph();
	
	public void loop() {
		while(true) {
			System.out.println("\nTESTER FOR LAB04 (TOPOLOGICAL SORTING)");
			System.out.println("\t1.Add Vertices");
			System.out.println("\t2.Add Edges");
			System.out.println("\t3.Set Weights");
			System.out.println("\t4.Show Weights");
			System.out.println("\t5.Show Critical Paths");
			System.out.println("\t6.Reset Graph");
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
				setWeight();
				break;
			case 4:
				showWeights();
				break;
			case 5:
				showCriticalPaths();
				break;
			case 6:
				in = new DirectedGraph();
				weights = new HashMap<String, Integer>();
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
	
	public void setWeight() {
		System.out.println("WHICH VERTEX > ");
		String n = scan.next();
		System.out.println("ENTER WEIGHT > ");
		int w = scan.nextInt();
		
		if (!weights.containsKey(n))
			weights.put(n , w);
		else 
			weights.replace(n, w);
	}
	
	public void showCriticalPaths() {
		WhyDontStructsExistInJava[] pathweights;
		String[] vertices = in.getVertices();
		
		try {
			pathweights = generateCriticalPaths(in, vertices, weights);
			System.out.println("CRITICAL PATHS : ");
		} catch (InputNotDAGException e) {
			System.out.println("ERROR : INPUT GRAPH IS NOT ACYCLIC");
			return;
		}
		
		System.out.println("\tVERTEX |\tCRITICAL TIME |\tCRITICAL PATH");
		for (int i = 0; i < vertices.length; ++i) {
			System.out.println("\t" + vertices[i] + " |\t" + pathweights[i].time + " |\t" + Stringmeupdaddy(i, pathweights, vertices, "") );
		}
	}
	
	public String Stringmeupdaddy(int ix, WhyDontStructsExistInJava[] ar, String[] vertices, String accum) {
		accum = vertices[ix] + " > " + accum;
		if (ar[ix].prev == -1) return accum;
		return Stringmeupdaddy(ar[ix].prev, ar, vertices, accum);
	}
	
	public void showWeights() {
		String[] vertices = in.getVertices();
			System.out.println("\tVERTEX \tWEIGHT");
		for (String str : vertices) {
			int weight = (weights.containsKey(str)) ? weights.get(str) : 1;
			System.out.println("\t" + str +"\t" + weight);
		}
	}
	
	private static class WhyDontStructsExistInJava {
		public final int prev;
		public final int time;
		public WhyDontStructsExistInJava(int prev, int time) {
			this.prev = prev;
			this.time = time;
		}
	}
}

class InputNotDAGException extends Exception {
	public InputNotDAGException() {
		super();
	}
}