import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TwoDTree tree = new TwoDTree("CrimeLatLonXY.csv");
        Scanner scanner = new Scanner(System.in);

        boolean quit = false;
        while (!quit) {
            System.out.println("What would you like to do?");
            System.out.println("1: inorder");
            System.out.println("2: preorder");
            System.out.println("3: levelorder");
            System.out.println("4: postorder");
            System.out.println("5: reverseLevelOrder");
            System.out.println("6: search for points within rectangle");
            System.out.println("7: search for nearest neighbor");
            System.out.println("8: quit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    tree.inOrderPrint();
                    break;
                case 2:
                    tree.preOrderPrint();
                    break;
                case 3:
                    tree.levelOrderPrint();
                    break;
                case 4:
                    tree.postOrderPrint();
                    break;
                case 5:
                    tree.reverseLevelOrderPrint();
                    break;
                case 6:
                    System.out.println("Enter rectangle bottom left (X1, Y1) and top right (X2, Y2) coordinates separated by spaces:");
                    String[] coordinates = scanner.nextLine().split(" ");
                    if (coordinates.length != 4) {
                        System.out.println("Invalid input. Please enter four doubles separated by spaces.");
                        break;
                    }
                    double x1 = Double.parseDouble(coordinates[0]);
                    double y1 = Double.parseDouble(coordinates[1]);
                    double x2 = Double.parseDouble(coordinates[2]);
                    double y2 = Double.parseDouble(coordinates[3]);
                    System.out.println("Searching for points within (" + x1 +"," + y1 +") and (" + x2 +"," +y2 +").");

                    ListOfCrimes crimesInRange = tree.findPointsInRange(x1, y1, x2, y2);
                    System.out.println("Examined " + crimesInRange.length() +" nodes during search.");
                    System.out.println(crimesInRange);
                    break;
                case 7:
                    // Implement code to search for nearest neighbor
                    break;
                case 8:
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 8.");
            }
        }

        scanner.close();



        /*tree.preOrderPrint();
        tree.inOrderPrint();
        tree.postOrderPrint();
        tree.reverseLevelOrderPrint();*/

        double x1 = 1350000; // Example lower x coordinate of range
        double y1 = 35000;  // Example lower y coordinate of range
        double x2 = 1360000; // Example upper x coordinate of range
        double y2 = 1000000; // Example upper y coordinate of range

        // Find crimes within the specified range
        ListOfCrimes crimesInRange = tree.findPointsInRange(x1, y1, x2, y2);

        // Print or process the crimes found
        printToKMLFile(crimesInRange.toKML(), "crimesInRange.kml");

        // Find the nearest neighbor to the specified point
        Neighbor nearest = tree.nearestNeighbor(1367227.0, 382502.0);

        if (nearest != null) {
            CrimeData nearestCrime = nearest.nearestNode.data;
            double distance = nearest.distance;

            System.out.println("Nearest crime:");
            System.out.println("Location: (" + nearestCrime.getX() + ", " + nearestCrime.getY() + ")");
            System.out.println("Offense: " + nearestCrime.getOffense());
            System.out.println("Distance: " + distance + " feet");
        } else {
            System.out.println("No crime data available.");
        }
    }

    public static void printToKMLFile(String kmlContent, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(kmlContent);
            System.out.println("KML file created successfully: " + fileName);
        } catch (IOException e) {
            System.err.println("Error occurred while writing KML file: " + e.getMessage());
        }
    }
}
