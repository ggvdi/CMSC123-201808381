package testers;

import java.util.Scanner;
import datastructures.graph.*;

public class GraphTester {
	Scanner scan = new Scanner(System.in);
	Graph graph = new UndirectedWeightedGraph();
	//Graph graph = new UndirectedGraph();

	public void showMenu() {
		System.out.println();
		System.out.println("\t1. Show vertices");
		System.out.println("\t2. Show adjacent vertices");
		System.out.println("\t3. Get vertex count");
		System.out.println("\t4. Get edge count");
		System.out.println("\t5. Add vertex");
		System.out.println("\t6. Add edge");
		System.out.println("\t7. Remove vertex");
		System.out.println("\t8. Remove edge");
		System.out.println("\t9. Check connectivity");
		System.out.println("\t0. Check adjacency");
		System.out.println("\tTRAVERSAL ALGORITHMS");
		System.out.println("\t11. Depth-first traversal");
		System.out.println("\t12. Breadth-first traversal");
		
		System.out.println("\tINPUT -1 TO EXIT");
		System.out.println();
	}
	
	public void loop() {
		System.out.println("WELCOME TO GRAPH TESTER");
		// code for doing stuff is in their own separate methods so that its not too messy
		while (true) {
			showMenu();
			int choice = scan.nextInt();
			if (choice == -1)
				break;
			
			switch(choice) {
				case 1 :
					showVertices();
					break;
				case 2 :
					showAdjacent();
					break;
				case 3 :
					System.out.print("VERTEX COUNT: ");
					System.out.println(graph.getVertexCount());
					break;
				case 4 :
					System.out.print("EDGE COUNT: ");
					System.out.println(graph.getEdgeCount());
					break;
				case 5 :
					addVertex();
					break;
				case 6 :
					addEdge();
					break;
				case 7 :
					removeVertex();
					break;
				case 8 :
					removeEdge();
					break;
				case 9 :
					checkConnectivity();
					break;
				case 0 :
					checkAdjacency();
					break;
				case 11 :
					depthFirstTraversal();
					break;
				case 12 :
					breadthFirstTraversal();
					break;
			}
		}
	}
	
	public void showVertices() {
		String[] v = graph.getVertices();
		
		System.out.print("VERTICES: [");
		for (int i = 0; i < v.length; ++i) 
			System.out.print(v[i] + ",");
		System.out.println("]");
	}
	
	
	public void showAdjacent() {
		System.out.print("ENTER VERTEX: ");
		String vertex = scan.next();
		String[] v = graph.getAdjacentVertices(vertex);
		
		System.out.print("VERTICES ADJACENT TO \"" + vertex +"\":[");
		for (int i = 0; i < v.length; ++i) 
			System.out.print(v[i] + ",");
		System.out.println("]");
	}
	
	public void addEdge() {
		System.out.print("ENTER VERTEX 1: ");
		String vertex1 = scan.next();
		System.out.print("ENTER VERTEX 2: ");
		String vertex2 = scan.next();
		
		graph.addEdge(vertex1, vertex2);
	}
	
	public void removeEdge() {
		System.out.print("ENTER VERTEX 1: ");
		String vertex1 = scan.next();
		System.out.print("ENTER VERTEX 2: ");
		String vertex2 = scan.next();
		
		graph.removeEdge(vertex1, vertex2);
	}
	
	public void checkConnectivity() {
		System.out.print("ENTER VERTEX 1: ");
		String vertex1 = scan.next();
		System.out.print("ENTER VERTEX 2: ");
		String vertex2 = scan.next();
		
		boolean connected = graph.isConnected(vertex1, vertex2);
		
		if (connected)
			System.out.println("VERTEX \"" + vertex1 +"\" AND \"" + vertex2 + "\" ARE CONNECTED ");
		else
			System.out.println("VERTEX \"" + vertex1 +"\" AND \"" + vertex2 + "\" ARE NOT CONNECTED ");
	}
	
	public void checkAdjacency() {
		System.out.print("ENTER VERTEX 1: ");
		String vertex1 = scan.next();
		System.out.print("ENTER VERTEX 2: ");
		String vertex2 = scan.next();
		
		boolean connected = graph.isAdjacent(vertex1, vertex2);
		
		if (connected)
			System.out.println("VERTEX \"" + vertex1 +"\" AND \"" + vertex2 + "\" ARE ADJACENT ");
		else
			System.out.println("VERTEX \"" + vertex1 +"\" AND \"" + vertex2 + "\" ARE NOT ADJACENT ");
	}
		
	public void addVertex() {
		System.out.print("ENTER VERTEX TO ADD: ");
		String vertex = scan.next();
		
		try {
			graph.addVertex(vertex);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void removeVertex() {
		System.out.print("ENTER VERTEX TO REMOVE: ");
		String vertex = scan.next();
		try {
			graph.removeVertex(vertex);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void depthFirstTraversal() {
		
		System.out.print("ENTER STARTING VERTEX: ");
		String vertex1 = scan.next();
		System.out.println("DEPTH-FIRST TRAVERSAL: ");
		
		String[] dft = graph.depthFirstTraversal(vertex1);
		System.out.print("[");
		for (int i  = 0; i < dft.length; ++i) 
			System.out.print(dft[i] + "->");
		System.out.println("(end)]");
	}
	
	public void breadthFirstTraversal() {
		System.out.print("ENTER STARTING VERTEX: ");
		String vertex1 = scan.next();
		System.out.println("BREADTH-FIRST TRAVERSAL: ");
		
		String[] bft = graph.breadthFirstTraversal(vertex1);
		System.out.print("[");
		for (int i  = 0; i < bft.length; ++i) 
			System.out.print(bft[i] + "->");
		System.out.println("end]");
	}
		
	public static void main(String[] args) {
		new GraphTester().loop();
	}
}