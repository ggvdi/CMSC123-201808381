package datastructures;

import java.util.ArrayList;

public class UndirectedAdjacencyListGraph extends Graph{
	private ArrayList<String> vertices;
	private ArrayList<ArrayList<String>> adjacencyLists;
	
	public UndirectedAdjacencyListGraph(String[] vertices, ArrayList<String>[] adjacencyList) {
		this.vertices = new ArrayList<String>();
		
		for (String i : vertices) {
			this.vertices.add(i);
		}
		
		for (ArrayList<String> i : adjacencyList) {
			adjacencyLists.add(i);
		}
	}
	
	public UndirectedAdjacencyListGraph() {
		this.vertices = new ArrayList<String>();
		this.adjacencyLists = new ArrayList<ArrayList<String>>();
	}
	
	private int getIndex(String vertex) {
		return 	vertices.indexOf(vertex);
	}
	
	public int getVertexCount() {
		return vertices.size();
	}
	
	public int getEdgeCount() {
		int count = 0;
		for (ArrayList<String> i : adjacencyLists )
			count += i.size();
		return count;
	}
	
	public String[] getAdjacentVertices(String vertex) {
		int ix = getIndex(vertex);
		
		ArrayList<String> adjacents = adjacencyLists.get(ix);
		
		String[] ret = new String[adjacents.size()];
		
		for (int i = 0; i < ret.length; ++i) {
			ret[i] = adjacents.get(i);
		}
		
		return ret;
	}
	
	public String[] getVertices() {
		String[] ret = new String[vertices.size()];
		
		for (int i = 0; i < ret.length; ++i) 
			ret[i] = vertices.get(i);
		
		return ret;
	}
	
	public boolean isAdjacent(String vertexA, String vertexB) {
		int ix = getIndex(vertexA);
		return adjacencyLists.get(ix).contains(vertexB);
	}
	
	private boolean isConnected(String vertexA, String vertexB, ArrayList<String> closedSet) {
		int ix = getIndex(vertexA);
		
		if (adjacencyLists.get(ix).contains(vertexB)) return true;
		
		closedSet.add(vertexA);
		for ( String a : adjacencyLists.get(ix)){
			if (!closedSet.contains(a))
				if ( isConnected(a, vertexB, closedSet)) return true; 
		}
		return false;
	}
	
	public void addVertex(String vertex) {
		vertices.add(vertex);
		adjacencyLists.add(new ArrayList<String>());
	}
	
	public void removeVertex(String vertex) {
		int ix = getIndex(vertex);
		
		vertices.remove(ix);
		adjacencyLists.remove(ix);
	}
	
	public void addEdge(String vertexA, String vertexB) {
		int ix1 = getIndex(vertexA);
		int ix2 = getIndex(vertexB);
		
		adjacencyLists.get(ix1).add(vertexB);
		adjacencyLists.get(ix2).add(vertexA);
	}
	
	public void removeEdge(String vertexA, String vertexB) {
		int ix1 = getIndex(vertexA);
		int ix2 = getIndex(vertexB);
		
		adjacencyLists.get(ix1).remove(vertexB);
		adjacencyLists.get(ix2).remove(vertexA);
	}
	
	public boolean isConnected(String vertexA, String vertexB) {
		ArrayList<String> closedSet = new ArrayList<String>();
		return isConnected(vertexA, vertexB, closedSet);
	}
	public static void main(String[] args) {
		ArrayList<String> a = new ArrayList<String>();
		a.add("DEPUTA");
		a.add("YAWA");
		a.add("UNSAMANA");
		String b = "LETSE";
		a.add(b);
		System.out.println(a.contains("YAWA"));
		System.out.println(a.indexOf("DEPUTA"));
		System.out.println(a.indexOf(b));
		System.out.println(a.indexOf("ADs"));
	}
	
}