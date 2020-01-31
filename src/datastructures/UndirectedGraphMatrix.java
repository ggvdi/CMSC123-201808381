package datastructures;

public class UndirectedGraphMatrix extends Graph {
	private boolean[][] adjacencyMatrix;
	private String[] vertices;
	private int vertexCount;
	
	public UndirectedGraphMatrix(String[] vertices, boolean[][] adjacencyMatrix) {
		this.adjacencyMatrix = adjacencyMatrix;
		this.vertices = vertices;
		vertexCount = vertices.length;
		
	}
	
	public String[] getVertices() {
		return vertices;
	}
	
	public int getVertexCount() {
		return vertexCount;
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
	}
	
	public int getIndex(String a) {
		for (int i = 0; i < vertexCount; ++i) {
			if (a.equals(vertices[i])) return i;
		}
		
		return -1;
	}
	
	public int getAdjacentCount(String a) {
		int ix = getIndex(a);
		getAdjacentCount(ix);
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
	
	public boolean isConnected() {
		//TODO implementation
		return false;
	}
	
	public boolean[][] getAdjacencyMatrix() {
		return adjacencyMatrix;
	}
	
	public void removeVertex(String vertex){
		//TODO
		//HAHAHAHAHAH
	}
}
