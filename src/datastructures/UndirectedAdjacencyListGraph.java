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
	
	public String[] depthFirstTraversal(String startingVertex) {
		int ix = getIndex(startingVertex);
		if (ix == -1) return null;
		
		ArrayList<String> closedSet = new ArrayList<String>();
		
		depthFirstTraversal(startingVertex, closedSet);
		String[] ret = new String[closedSet.size()];
		
		for (int i = 0; i < ret.length; ++i) {
			ret[i] = closedSet.get(i);
		}
		
		return ret;
	}
	
	private void depthFirstTraversal(String vertex, ArrayList<String> closedSet) {
		int ix = getIndex(vertex);
		
		closedSet.add(vertex);
		
		for (String adjacent : adjacencyLists.get(ix)) {
			if ( !closedSet.contains(adjacent) )
				depthFirstTraversal(adjacent, closedSet);
		}
	}
	
	public String[] breadthFirstTraversal(String startingVertex) {
		Queue q = new Queue();
		ArrayList<String> closedSet = new ArrayList<String>();
		q.enqueue(startingVertex);
		closedSet.add(startingVertex);
		
		String curr;
		while ( (curr = q.dequeue()) != null ) {
			int ix = getIndex(curr);
			for (String vertex : adjacencyLists.get(ix)) {
				if (!closedSet.contains(vertex)) {
					closedSet.add(curr);
					q.enqueue(curr);
				}
			}
		}
		
		String[] ret = new String[closedSet.size()];
		
		for (int i = 0; i < ret.length; ++i) {
			ret[i] = closedSet.get(i);
		}
		
		return ret;
	}
	
}

class Queue {
	class Node{
		public String data;
		public Node next;
		public Node(String data){
			this.data = data;
			this.next = null;
		}
	}
	
	private Node front = null;
	
	public String dequeue() {
		if (front == null) return null;
		
		return front.data;
	}
	
	public void enqueue(String data) {
		if (front == null) {
			front = new Node(data);
			return;
		}
		Node a = front;
		while (a.next != null)
			a = a.next;
		a.next = new Node(data);
	}
}