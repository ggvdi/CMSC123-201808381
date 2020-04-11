public class Dummy {
	public boolean[][] adjacencyMatrix;
	public String[] vertexNames;
	
	private int getIndex(String vertexName) {
		for (int i = 0; i < vertexNames.length; ++i) {
			if (vertexNames[i].equals(vertexName)) return i;
		}
		return -1;
	}
	
	private void resizeMatrices(int newSize) {
		String[] n_vertexNames = new String[newSize];
		boolean[][] n_adjacencyMatrix = new boolean[newSize][newSize];
	
		int size = Math.min(vertexNames.length, newSize);
		for (int i = 0; i < size; ++i) {
			n_vertexNames[i] = vertexNames[i];
			for (int j = 0; j < size; ++j) {
				n_adjacencyMatrix[i][j] = adjacencyMatrix[i][j];
			}
		}
		
		adjacencyMatrix = n_adjacencyMatrix;
		vertexNames = n_vertexNames;
	}
	
	private void removeItem(int index) {
		int size = vertexNames.length;
		
		for (int i = index; i + 1 < size; ++i) {
			vertexNames[i] = vertexNames[i + 1];
			adjacencyMatrix[i] = adjacencyMatrix[i + 1];
		}
		
		for (int i = 0; i < size; ++i) {
			for (int j = index; j + 1 < size; ++j) {
				adjacencyMatrix[i][j] = adjacencyMatrix[i][j + 1];
			}
		}
		
		resizeMatrices(size - 1);
	}
	
	public void printMatrix() {
		for (int i = 0; i < vertexNames.length; ++i) {
			System.out.print("ROW \"" + vertexNames[i] + "\" [");
			for (int j = 0; j < vertexNames.length; ++j) {
				System.out.print(adjacencyMatrix[i][j] + ",");
			}
			System.out.println("]");
		}
	}
	
	public static void main(String[] args) {
		Dummy d = new Dummy();
		boolean[][] dick = {
			{ false, false, false, false, false },
			{ false, false, true , false, false },
			{ false, true , true , true , false },
			{ false, false, true , false, false },
			{ false, false, false, false, false }
		};
		
		String[] bitch = {"a", "b", "c", "d", "e"};
		d.vertexNames = bitch;
		d.adjacencyMatrix = dick;
		d.printMatrix();
		d.removeItem(0);
		d.printMatrix();
		d.resizeMatrices(6);
		d.printMatrix();
	}
}