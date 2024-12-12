package part1;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

	private static int start;
	private static int end;
	private static double[][] adjacencyMatrix;
	private static double[][] adjacencyMatrixMST;

	/**
	 * main function which is responsible for simple input and output functions
	 */
	public static void main(String[] args) throws IOException {

		// TSPChecker to store distance matrix data
		TSPChecker checker = new TSPChecker();
		int number_of_vertices;
		Scanner scan = new Scanner(System.in);
		try {
			System.out.println("Enter start index:");
			start = scan.nextInt();

			System.out.println("Enter end index:");
			end = scan.nextInt();

			System.out.println("Crime Records Processed:");
			System.out.println();

			number_of_vertices = end - start + 1;
			adjacencyMatrix = new double[number_of_vertices][number_of_vertices];
			adjacencyMatrixMST = new double[number_of_vertices + 1][number_of_vertices + 1];

			for (int i = 0; i < number_of_vertices; i++) {
				showStringList(checker.memory.get(start + i));

				Double x1 = Double
						.parseDouble(checker.memory.get(start + i)[0]);
				Double y1 = Double
						.parseDouble(checker.memory.get(start + i)[1]);

				for (int j = 0; j < number_of_vertices; j++) {
					Double x2 = Double.parseDouble(checker.memory
							.get(start + j)[0]);
					Double y2 = Double.parseDouble(checker.memory
							.get(start + j)[1]);
					adjacencyMatrix[i][j] = computeDistance(x1, x2, y1, y2);
				}
			}

			// MSTFinder to find MST from the adjacent matrix
			MSTFinder mstFinder = new MSTFinder(number_of_vertices);
			mstFinder.prims(adjacencyMatrix);
			adjacencyMatrixMST = mstFinder.convertAdjencyMatrix();

			// Traverse the MST returned from previoius steps to find the path
			TraverseMST traver = new TraverseMST(adjacencyMatrix,
					adjacencyMatrixMST);
			String resultString = traver.traverse();
			System.out.println(resultString);

		} catch (InputMismatchException inputMismatch) {
			System.out.println("Wrong input");
		}
		scan.close();

	}

	/**
	 * print array of strings
	 */
	public static void showStringList(String[] sl) {
		for (String s : sl) {
			System.out.print(s);
			System.out.print(',');
		}
		System.out.println();
	}

	/**
	 * main compute distances between coordinates
	 */
	public static double computeDistance(double x1, double x2, double y1,
			double y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}

}
