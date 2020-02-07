import datastuctures.Tree;
import datastuctures.graph.*;

public class LAB04 {
	
	public static int getIndexOf(String str, String[] arr) {
		for (int i = 0; i < arr.length; ++i) {
			if (str.equals(arr[i])) return i;
		}
		return -1;
	}
	
	public static int[] generateInDegrees(DirectedGraph DG, String[] vertices) {
		int[] degrees = new int[vertices.length];
		for (String vertex : vertices) {
			String[] adj = DG.getAdjacentVertices(vertices);
			for (String str : adj) {
				degrees[getIndexOf(str, vertices)] += 1;
			}
		}
		
		return degrees;
	}
	
	public static ArrayList<String> getZeroes(String[] vertices, int[] degrees) {
		ArrayList<String> ret = new ArrayList<String>();
		for (int i = 0; i < vertices.length; ++i) {
			if (degrees[i] == 0) ret.add(vertices[i]);
		}
		
		return ret;
	}
	
	//vertices and inDegrees should really be compressed into one thing
	public int[] generateSubGraph(Tree<String> subpath, String[] vertices, int[] inDegrees) {
		// -1 = doesnt exist in subgraph
		// everything else follows normal indegrees
		// set indegree[subpath] to -1 i.e. remove subpath from subgraph;
		// for every adjacent of subpath then indegrees -1 for that vertex
		// return adasdasda
	}
	
	public void generatePaths(Tree<String> path, String[] vertices, int[] inDegrees) {
		// generate subgraph for path
		// get zeroes
		// for every zero
		// add zero to path's child
		// generatePath for each zero
		// lets ddddddddddd-d-d-d-d-d-d-d-d-d-d-d-d-dduel 
	}
	
	public static void topologicalSort(DirectedGraph DG) throws InputNotDAGException() {
		Tree<String> paths = new Tree<String>();
		String[] vertices = DG.getVertices();
		int inDegrees = generateInDegrees(DG, vertices);
		
		ArrayList<String> zeroes = getZeroes(vertices, degrees);
		if (zeroes.size() == 0) throw new InputNotDAGException();
		
		for (String str : zeroes) {
			paths.addChild(str);
			/////// for each child of path;
			///////       generate subgraph
			///////       get zeroes(subgraph)
			///////       if no zeroes throw InputNotDAG
			///////       add zeroes(subgraph) to childs' children
			///////       recursive call ?
			/////// finally return depthfirstsearch of path
		}
	}
	
	public static void main(String[] args) {
	}
}

class InputNotDAGException extends Exception {
	public InputNotDAGException() {
		super();
	}
}