package part2;

import java.util.ArrayList;
import java.util.Stack;

public class TraverseMST {
	public double[][] matrix;
	public double[][] matrixAdj;
	public ArrayList<Integer> path = new ArrayList<Integer>();
	private ArrayList<Integer> result = new ArrayList<>();

	public TraverseMST(double[][] adjacency_matrix, double[][] adjacency_matrix_MST) {
		this.matrix = adjacency_matrix_MST;
		this.matrixAdj = adjacency_matrix;
	}

	public ArrayList<Integer> getResult() {
		return result;
	}

	public String traverse() {
		String cyclePath = computeCyclePath();
		double cycleLength = computeCycleLength();
		return formatResults(cyclePath, cycleLength);
	}

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
			result.add(i - 1);
		}
		pathBuilder.append("0");
		result.add(0);
		return "\nHamiltonan Cycle (not necessarily optimum): " + pathBuilder.toString() + "\n";
	}

	private double computeCycleLength() {
		double length = 0;
		for (int i = 1; i < path.size(); i++) {
			length += matrixAdj[path.get(i - 1) - 1][path.get(i) - 1];
		}
		length += matrixAdj[path.get(path.size() - 1) - 1][0];
		return length / 5280;
	}

	private String formatResults(String cyclePath, double cycleLength) {
		return cyclePath + "Length of Cycle :" + cycleLength + " miles";
	}
}
