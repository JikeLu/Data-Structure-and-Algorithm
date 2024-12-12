package part2;

import java.util.Arrays;

public class MSTFinder {
	private boolean unsettled[];
	private boolean settled[];

	private int numberOfVertices;
	private double adjMatrix[][];
	public double MSTadjMatrix[][];
	private double distanceToAll[];
	private int source[];

	public MSTFinder(int nov) {
		this.numberOfVertices = nov;
		unsettled = new boolean[nov + 1];
		settled = new boolean[nov + 1];
		adjMatrix = new double[nov + 1][nov + 1];
		MSTadjMatrix = new double[nov + 1][nov + 1];
		distanceToAll = new double[nov + 1];

		for (int i = 0; i < nov + 1; i++) {
			Arrays.fill(MSTadjMatrix[i], -1);
		}

		Arrays.fill(distanceToAll, Double.MAX_VALUE);
		Arrays.fill(unsettled, true);
		Arrays.fill(settled, false);

		source = new int[nov + 1];
	}

	public boolean hasUnsettled(boolean unsettled[]) {
		int count = 0;
		for (boolean isUnsettled : unsettled) {
			if (isUnsettled) {
				count++;
			}
		}
		return count > 0;
	}

	public void prims(double adjacencyMatrix[][]) {
		initializeAdjacencyMatrix(adjacencyMatrix);
		initializeSource();

		while (hasUnsettledVertices()) {
			int nextVertex = selectNextVertex();
			settleVertex(nextVertex);
			updateDistancesFromTree(nextVertex);
		}
	}

	private void initializeAdjacencyMatrix(double adjacencyMatrix[][]) {
		for (int i = 1; i <= numberOfVertices; i++) {
			for (int j = 1; j <= numberOfVertices; j++) {
				this.adjMatrix[i][j] = adjacencyMatrix[i - 1][j - 1];
			}
		}
	}

	private void initializeSource() {
		distanceToAll[1] = 0;
		source[1] = 1;
	}

	private boolean hasUnsettledVertices() {
		return hasUnsettled(unsettled);
	}

	private int selectNextVertex() {
		return getNextVertex();
	}

	private void settleVertex(int vertex) {
		settled[vertex] = true;
		unsettled[vertex] = false;
	}

	private void updateDistancesFromTree(int vertex) {
		updateDistances(vertex);
	}

	private int getNextVertex() {
		double min = Double.MAX_VALUE;
		int vertex = 0;
		for (int v = 1; v <= numberOfVertices; v++) {
			if (unsettled[v] && distanceToAll[v] < min) {
				vertex = v;
				min = distanceToAll[v];
			}
		}
		return vertex;
	}

	public void updateDistances(int newAddedVertex) {
		for (int destinationvertex = 1; destinationvertex <= numberOfVertices; destinationvertex++) {
			int newDistance = 0;
			if (!settled[destinationvertex]) {
				newDistance = (int) adjMatrix[newAddedVertex][destinationvertex];
				if (newDistance < distanceToAll[destinationvertex]) {
					distanceToAll[destinationvertex] = newDistance;
					source[destinationvertex] = newAddedVertex;
				}
			}
		}
	}

	public double[][] convertAdjencyMatrix() {
		for (int vertex = 2; vertex <= numberOfVertices; vertex++) {
			MSTadjMatrix[source[vertex]][vertex] = adjMatrix[source[vertex]][vertex];
		}
		return MSTadjMatrix;
	}
}