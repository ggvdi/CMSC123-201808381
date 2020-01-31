package datastructures;

public abstract class WeightedGraph {
	public abstract double getEdgeWeight(String vertexA, String vertexB);
	public abstract double getPathWeight(String vertexA, String vertexB);
}