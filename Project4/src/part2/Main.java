/**
 * Jike Lu, jikelu
 */

package part2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

	private static int startIndex;
	private static int endIndex;
	private static double[][] adjMatrix;
	private static double[][] MSTadjMatrix;

	public static void main(String[] args) throws IOException {

		CrimeData data = new CrimeData();
		int number_of_vertices;
		Scanner scan = new Scanner(System.in);
		try {
			String startDate, endDate;
			System.out.println("Enter start date:");
			startDate = scan.next();
			startIndex = data.findStartDateIndex(startDate);

			System.out.println("Enter end date:");
			endDate = scan.next();
			endIndex = data.findEndDateIndex(endDate);

			System.out.println("Crime Records Processed:");
			System.out.println();

			number_of_vertices = endIndex - startIndex + 1;
			adjMatrix = new double[number_of_vertices][number_of_vertices];
			MSTadjMatrix = new double[number_of_vertices + 1][number_of_vertices + 1];
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

			MSTFinder mstFinder = new MSTFinder(number_of_vertices);
			mstFinder.prims(adjMatrix);
			MSTadjMatrix = mstFinder.convertAdjencyMatrix();

			TraverseMST traver = new TraverseMST(adjMatrix, MSTadjMatrix);
			String resultStringMST = traver.traverse();
			System.out.println(resultStringMST);

			BruteForce bruteForce = new BruteForce(adjMatrix);
			String resultString = bruteForce.bfResult();
			System.out.println(resultString);

			writeKML(traver, bruteForce, data);

		} catch (InputMismatchException inputMismatch) {
			System.out.println("Wrong input");
		}
		scan.close();

	}

	// Method to write KML file
	public static void writeKML(TraverseMST traver, BruteForce bruteForce, CrimeData data) {
		try {
			FileWriter writer = new FileWriter("PGHCrimes.kml");
			writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
			writer.write("<kml xmlns=\"http://earth.google.com/kml/2.2\">\n");
			writer.write("<Document>\n");
			writer.write("<name>Pittsburgh TSP</name><description>TSP on Crime</description>\n");

			writer.write("<Style id=\"style6\">\n");
			writer.write("<LineStyle>\n");
			writer.write("<color>73FF0000</color>\n");
			writer.write("<width>5</width>\n");
			writer.write("</LineStyle>\n");
			writer.write("</Style>\n");

			writer.write("<Style id=\"style5\">\n");
			writer.write("<LineStyle>\n");
			writer.write("<color>507800F0</color>\n");
			writer.write("<width>5</width>\n");
			writer.write("</LineStyle>\n");
			writer.write("</Style>\n");

			writer.write("<Placemark>\n");
			writer.write("<name>TSP Path</name>\n");
			writer.write("<description>TSP Path</description>\n");
			writer.write("<styleUrl>#style6</styleUrl>\n");
			writer.write("<LineString>\n");
			writer.write("<tessellate>1</tessellate>\n");
			writer.write("<coordinates>\n");
			for (int i : traver.getResult()) {
				String[] lineData = data.linesOfData.get(startIndex + i);
				writer.write(lineData[8] + "," + lineData[7] + ",0.000000\n");
			}
			writer.write("</coordinates>\n");
			writer.write("</LineString>\n");
			writer.write("</Placemark>\n");

			writer.write("<Placemark>\n");
			writer.write("<name>Optimal Path</name>\n");
			writer.write("<description>Optimal Path</description>\n");
			writer.write("<styleUrl>#style5</styleUrl>\n");
			writer.write("<LineString>\n");
			writer.write("<tessellate>1</tessellate>\n");
			writer.write("<coordinates>\n");
			for (int i : bruteForce.getResult()) {
				String[] lineData = data.linesOfData.get(startIndex + i);
				writer.write(lineData[8] + "," + lineData[7] + ",0.000000\n");
			}
			writer.write("</coordinates>\n");
			writer.write("</LineString>\n");
			writer.write("</Placemark>\n");

			writer.write("</Document>\n");
			writer.write("</kml>\n");

			writer.close();
			System.out.println("KML file generated successfully.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Method to display String list
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
