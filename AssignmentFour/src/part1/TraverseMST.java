package part1;
import java.util.ArrayList;
import java.util.Stack;

/**
 * The class traverse the MST to find the circle path
 */
public class TraverseMST {
	public double[][] matrix;
	public double[][] matrixAdj;
	public ArrayList<Integer> path = new ArrayList<Integer>();

	public TraverseMST(double[][] adjacency_matrix,
			double[][] adjacency_matrix_MST) {
		this.matrix = adjacency_matrix_MST;
		this.matrixAdj = adjacency_matrix;
	}

	/**
	 * Traverse the MST to find the circle path
	 */
	public String traverse() {

		// traverse and compute path
		String ret = "";
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

		for (int i : path) {
			ret += Integer.toString(i - 1) + " ,";
		}

		ret += "0";
		ret = "\nHamiltonan Cycle (not necessarily optimum): " + ret + "\n";

		// traverse and compute length
		double length = 0;
		for (int i = 1; i < path.size(); i++) {
			length += matrixAdj[path.get(i - 1) - 1][path.get(i) - 1];
		}
		length += matrixAdj[path.get(path.size() - 1) - 1][0];

		ret = ret + "Length of Cycle :" + length / 5280 + " miles";
		return ret;
	}

}
