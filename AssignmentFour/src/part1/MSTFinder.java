package part1;
import java.util.Arrays;

/**
 * The class find minimun spanning tree and return an adjacent matrix
 */
public class MSTFinder {
	// use unsettled and settled as flag arrays
	private boolean unsettled[];
	private boolean settled[];

	private int numberOfVertices;
	private double adjacencyMatrix[][];
	public double adjacencyMatrixForMST[][];
	private double distanceFromExistingTreeToAllUnsettledVertex[];
	private int source[];

	public MSTFinder(int numberofvertices) {
		this.numberOfVertices = numberofvertices;
		unsettled = new boolean[numberofvertices + 1];
		settled = new boolean[numberofvertices + 1];
		adjacencyMatrix = new double[numberofvertices + 1][numberofvertices + 1];
		adjacencyMatrixForMST = new double[numberofvertices + 1][numberofvertices + 1];
		distanceFromExistingTreeToAllUnsettledVertex = new double[numberofvertices + 1];

		for (int i = 0; i < numberofvertices + 1; i++) {
			Arrays.fill(adjacencyMatrixForMST[i], -1);
		}

		Arrays.fill(distanceFromExistingTreeToAllUnsettledVertex,
				Double.MAX_VALUE);
		Arrays.fill(unsettled, true);
		Arrays.fill(settled, false);

		source = new int[numberofvertices + 1];
	}

	/**
	 * check if the graph still has unsettled vertices
	 */
	public boolean hasUnsettled(boolean unsettled[]) {
		int count = 0;
		for (int index = 0; index < unsettled.length; index++) {
			if (unsettled[index]) {
				count++;
			}
		}
		return count > 0;
	}

	/**
	 * prims algorithm to find MST
	 */
	public void prims(double adjacencyMatrix[][]) {

		for (int i = 1; i <= numberOfVertices; i++) {
			for (int j = 1; j <= numberOfVertices; j++) {
				this.adjacencyMatrix[i][j] = adjacencyMatrix[i - 1][j - 1];
			}
		}

		distanceFromExistingTreeToAllUnsettledVertex[1] = 0;
		source[1] = 1;

		while (hasUnsettled(unsettled)) {
			int nextAddedVertex = getNextVertex();
			settled[nextAddedVertex] = true;
			unsettled[nextAddedVertex] = false;
			updateDistanceFromExistingTreeToAllUnsettledVertex(nextAddedVertex);
		}
	}

	/**
	 * settle the next vertex from the unsettled vertexes
	 */
	private int getNextVertex() {
		double min = Double.MAX_VALUE;
		int vertex = 0;
		for (int v = 1; v <= numberOfVertices; v++) {
			if (unsettled[v]
					&& distanceFromExistingTreeToAllUnsettledVertex[v] < min) {
				vertex = v;
				min = distanceFromExistingTreeToAllUnsettledVertex[v];
			}
		}
		return vertex;
	}

	/**
	 * Update weights from existing tree to all unsettled vertex
	 */
	public void updateDistanceFromExistingTreeToAllUnsettledVertex(
			int newAddedVertex) {
		for (int destinationvertex = 1; destinationvertex <= numberOfVertices; destinationvertex++) {
			if (settled[destinationvertex] == false) {
				if (adjacencyMatrix[newAddedVertex][destinationvertex] < distanceFromExistingTreeToAllUnsettledVertex[destinationvertex]) {
					distanceFromExistingTreeToAllUnsettledVertex[destinationvertex] = adjacencyMatrix[newAddedVertex][destinationvertex];
					source[destinationvertex] = newAddedVertex;
				}
			}
		}
	}

	/**
	 * Represent the MST as a adjacent matrix
	 */
	public double[][] convertAdjencyMatrix() {
		for (int vertex = 2; vertex <= numberOfVertices; vertex++) {
			adjacencyMatrixForMST[source[vertex]][vertex] = adjacencyMatrix[source[vertex]][vertex];
		}

		return adjacencyMatrixForMST;

	}

}