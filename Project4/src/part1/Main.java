/**
 * Jike Lu, jikelu
 */

package part1;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

	private static int startIndex;
	private static int endIndex;
	private static double[][] adjMatrix;
	private static double[][] MSTadjMatrix;

	public static void main(String[] args) throws IOException {

		// Creating an instance of CrimeData to process crime data
		CrimeData data = new CrimeData();

		// Declaring variables
		int number_of_vertices;
		Scanner scan = new Scanner(System.in);
		try {
			String startDate, endDate;

			// Prompting the user to enter the start date
			System.out.println("Enter start date:");
			startDate = scan.next();
			startIndex = data.findStartDateIndex(startDate);

			// Prompting the user to enter the end date
			System.out.println("Enter end date:");
			endDate = scan.next();
			endIndex = data.findEndDateIndex(endDate);

			// Calculating the number of vertices based on the start and end date
			number_of_vertices = endIndex - startIndex + 1;

			// Initializing adjacency matrices
			adjMatrix = new double[number_of_vertices][number_of_vertices];
			MSTadjMatrix = new double[number_of_vertices + 1][number_of_vertices + 1];

			// Processing crime records and populating the adjacency matrix
			for (int i = 0; i < number_of_vertices; i++) {
				showStringList(data.linesOfData.get(startIndex + i));

				Double x1 = Double.parseDouble(data.linesOfData.get(startIndex + i)[0]);
				Double y1 = Double.parseDouble(data.linesOfData.get(startIndex + i)[1]);

				for (int j = 0; j < number_of_vertices; j++) {
					Double x2 = Double.parseDouble(data.linesOfData.get(startIndex + j)[0]);
					Double y2 = Double.parseDouble(data.linesOfData.get(startIndex + j)[1]);
					adjMatrix[i][j] = computeDistance(x1, y1, x2, y2);
				}
			}

			// Finding Minimum Spanning Tree (MST)
			MSTFinder mstFinder = new MSTFinder(number_of_vertices);
			mstFinder.prims(adjMatrix);
			MSTadjMatrix = mstFinder.convertAdjencyMatrix();

			// Traversing MST and generating result
			TraverseMST traver = new TraverseMST(adjMatrix, MSTadjMatrix);
			String resultString = traver.traverse();
			System.out.println(resultString);

		} catch (InputMismatchException inputMismatch) {
			System.out.println("Wrong input");
		} finally {
			// Closing scanner
			scan.close();
		}

	}

	// Method to display elements of a string array
	public static void showStringList(String[] sl) {
		for (String s : sl) {
			System.out.print(s);
			System.out.print(',');
		}
		System.out.println();
	}

	// Method to compute distance between two points
	public static double computeDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}
}
