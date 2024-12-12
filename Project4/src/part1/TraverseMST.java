package part1;

import java.util.ArrayList;
import java.util.Stack;

public class TraverseMST {
	public double[][] matrix;
	public double[][] matrixAdj;
	public ArrayList<Integer> path = new ArrayList<Integer>();

	// Constructor to initialize TraverseMST object with adjacency matrices
	public TraverseMST(double[][] adjacency_matrix, double[][] adjacency_matrix_MST) {
		this.matrix = adjacency_matrix_MST;
		this.matrixAdj = adjacency_matrix;
	}

	// Method to traverse the Minimum Spanning Tree (MST) and compute cycle path and length
	public String traverse() {
		String cyclePath = computeCyclePath();
		double cycleLength = computeCycleLength();
		return formatResults(cyclePath, cycleLength);
	}

	// Method to compute cycle path using Depth First Search (DFS)
	private String computeCyclePath() {
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(1);

		while (!stack.empty()) {
			int cur = stack.pop();
			path.add(cur);

			for (int i = matrix[1].length - 1; i >= 1; i--) {
				if (matrix[cur][i] > 0) {
					stack.push(i);
				}
			}
		}

		StringBuilder pathBuilder = new StringBuilder();
		for (int i : path) {
			pathBuilder.append(Integer.toString(i - 1)).append(" ,");
		}
		pathBuilder.append("0");
		return "\nHamiltonan Cycle (not necessarily optimum): " + pathBuilder.toString() + "\n";
	}

	// Method to compute cycle length by summing up edge weights
	private double computeCycleLength() {
		double length = 0;
		for (int i = 1; i < path.size(); i++) {
			length += matrixAdj[path.get(i - 1) - 1][path.get(i) - 1];
		}
		length += matrixAdj[path.get(path.size() - 1) - 1][0];
		return length / 5280; // Converting length to miles
	}

	// Method to format results with cycle path and length
	private String formatResults(String cyclePath, double cycleLength) {
		return cyclePath + "Length of Cycle :" + cycleLength + " miles";
	}
}
