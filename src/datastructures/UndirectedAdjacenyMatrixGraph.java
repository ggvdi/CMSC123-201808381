package datastructures;

import java.util.ArrayList;

public class UndirectedAdjacenyMatrixGraph extends Graph {
	private boolean[][] adjacencyMatrix;
	private String[] vertices;
	private int vertexCount;
	
	public UndirectedAdjacenyMatrixGraph(String[] vertices, boolean[][] adjacencyMatrix) {
		this.adjacencyMatrix = adjacencyMatrix;
		this.vertices = vertices;
		vertexCount = vertices.length;
		
	}
	
	public UndirectedAdjacenyMatrixGraph() {
		this.adjacencyMatrix = new boolean[0][0];
		this.vertices = new String[0];
		
		vertexCount = vertices.length;
	}
	
	public String[] getVertices() {
		return vertices;
	}
	
	public int getVertexCount() {
		return vertices.length;
	}
	
	public int getEdgeCount() {
		//assuming that the transpose of the adjacencyMatrix is  still equal to the adjacencyMatrix;
		//this should happen since if the graph is undirected then the edges should go both ways
		//this sounds convoluted so maybe change this somewhen later;
		int count = 0;
		for (int i = 0; i < vertexCount; ++i) {
			for (int j = i; j < vertexCount; ++j) {
				if (adjacencyMatrix[i][j]) ++count;
			}
		}
		
		return count;
	}
	
	public int getIndex(String a) {
		for (int i = 0; i < vertexCount; ++i) {
			if (a.equals(vertices[i])) return i;
		}
		
		return -1;
	}
	
	public int getAdjacentCount(String a) {
		int ix = getIndex(a);
		return getAdjacentCount(ix);
		
	}
	
	public int getAdjacentCount(int ix) {
		if (ix < 0) return -1;
		
		int count = 0;
		for (int i = 0; i < vertexCount; ++i) {
			if (adjacencyMatrix[ix][i]) ++count;
		}
		
		return count;
	}
	
	public String[] getAdjacent(String a) {
		int ix = getIndex(a);
		if (ix < 0) return null;
		
		int adjacentCount = getAdjacentCount(ix);
		if (adjacentCount <= 0) return null;
		
		String[] ret = new String[adjacentCount];
		int cursor = 0;
		
		for (int i = 0; i < vertexCount; ++i){
			if (adjacencyMatrix[ix][i])
				ret[cursor++] = vertices[i];
		}
			
		return ret;
	}
	
	public boolean isAdjacent(String a, String b) {
		int ix1 = getIndex(a);
		int ix2 = getIndex(b);
		return adjacencyMatrix[ix1][ix2];
	}
	
	public void addEdge(String a, String b) {
		int ix1 = getIndex(a);
		int ix2 = getIndex(b);
		
		adjacencyMatrix[ix1][ix2] = true;
		adjacencyMatrix[ix2][ix1] = true;
	}
	
	public void removeEdge(String a, String b) {
		int ix1 = getIndex(a);
		int ix2 = getIndex(b);
		
		adjacencyMatrix[ix1][ix2] = false;
		adjacencyMatrix[ix2][ix1] = false;
	}
	
	public void addVertex(String vertex) {
		String[] n_vertices = new String[vertices.length + 1];
		for (int i = 0; i < vertices.length; ++i) {
			n_vertices[i] = vertices[i];
		}
		n_vertices[vertices.length] = vertex;
		vertices = n_vertices;
		vertexCount++;
	}
	
	public boolean isConnected(String vertexA, String vertexB) {
		ArrayList<String> openSet = new ArrayList<String>();
		ArrayList<String> closedSet = new ArrayList<String>();
		
		openSet.add(vertexA);
		while (openSet.size() != 0) {
			String a = openSet.get(0);
			int ix = getIndex(a);
			
			for (int i = 0; i < adjacencyMatrix[ix].length; ++i) {
				if (adjacencyMatrix[ix][i]) {
					openSet.add(vertices[i]);
					if (vertices[i] == vertexB) return true;
				}
			}
			
			closedSet.add(a);
			openSet.remove(0);
		}
		return false;
	}
	
	public boolean[][] getAdjacencyMatrix() {
		return adjacencyMatrix;
	}
	
	public void removeVertex(String vertex){
		int ix = getIndex(vertex);
		
		String[] ar = new String[vertices.length - 1];
		int counter = 0;
		for (int i = 0; i < vertices.length; ++i) {
			if ( i != ix ) ar[counter++] = vertices[i];
		}
		
		vertices = ar;
	}
	
	public String[] getAdjacentVertices(String vertex) {
		return null;
	}
}