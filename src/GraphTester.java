import java.util.Scanner;
import datastructures.*;

public class GraphTester {
	Scanner scan = new Scanner(System.in);
	Graph graph = new UndirectedAdjacencyListGraph();

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
		System.out.println("\tINPUT -1 TO EXIT");
		System.out.println();
	}
	
	public void loop() {
		System.out.println("WELCOME TO GRAPH TESTER");
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
		graph.addVertex(vertex);
	}
	
	public void removeVertex() {
		System.out.print("ENTER VERTEX TO REMOVE: ");
		String vertex = scan.next();
		graph.removeVertex(vertex);
	}
	
		
	public static void main(String[] args) {
		new GraphTester().loop();
	}
}