/**
 * Author: Jikelu
 * ID: jikelu
 */



import java.io.*;
import java.util.Arrays;

public class Main {
    static RedBlackTree rbtree = new RedBlackTree();
    static StringBuilder stringbuilder = new StringBuilder();
    static StringBuilder names = new StringBuilder();
    static boolean print = false;

    public static void main(String[] args) throws IOException {
        try {
            BufferedReader in =
                    new BufferedReader(
                            new FileReader(args[0])
                    );
            String line;
            line = in.readLine();

            while(line != null) {
                processLine(line);
                line = in.readLine();
            }
        } catch(IOException e) {
            System.out.println("IO Exception"); }

        int[][] graph = buildGraph();
        PrintWriter writer = new PrintWriter(new FileOutputStream(
                "result.txt",
                true /* append = true */));
        if (print == false) {
            System.out.println("jikelu");
            writer.println("jikelu");
            print = true;
        }

        // print matrix
        for (int i = 0; i < graph.length; i++) {
            System.out.println(graphFormat(graph[i]));
            writer.println(graphFormat(graph[i]));
        }
        System.out.println();
        writer.println("");
        // call greedy()
        String raw_schedule = greedy(graph);
        String[] schedule = schedule(raw_schedule);
        for (int i = 0; i < schedule.length; i++) {
            System.out.println(schedule[i]);
            writer.println(schedule[i]);
        }
        writer.println("");
        writer.close();

    }
    public static void processLine(String line) {
        StringBuilder sb = new StringBuilder();
        String[] data = line.split(" ");
        int num = Integer.parseInt(data[1]);
        int[] courses = new int[num];
        for (int i = 2; i < 2 + num; i++) {
            if (!rbtree.contains(data[i])) {
                rbtree.insert(data[i]);
                names.append(data[i]).append('!');
            }
            courses[i-2] = rbtree.getID(data[i]);
            for (int j = 0; j < i-2 ; j++) {
                sb.append(courses[i-2]).append(',').append(courses[j]);
                sb.append('&');
            }
        }
//        System.out.println("rbtree: "+rbtree.toString());
        stringbuilder.append(sb);
        System.out.println("stringbuilder: "+stringbuilder);
    }
    public static int[][] buildGraph() {
        int size = rbtree.getSize();
        String s = stringbuilder.toString();

        int[][] graph = new int[size][size];
        String[] arr = s.split("&");
        for (String pair: arr) {
            int i  = Integer.parseInt(pair.split(",")[0]);
            int j = Integer.parseInt(pair.split(",")[1]);
            graph[i][j] = 1;
            graph[j][i] = 1;
        }
        return graph;
    }
    public static String greedy(int[][] adjacencyMatrix) {
        // Initialize variables
        int[] vertices = new int[adjacencyMatrix.length];
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            vertices[i] = i; // Fill vertices array with vertex numbers
        }

        StringBuilder coloringResult = new StringBuilder();
        boolean vertexFound;

        while (vertices[0] != -1) { // Continue until all vertices are processed
            int[] currentColorGroup = new int[adjacencyMatrix.length];
            Arrays.fill(currentColorGroup, -1); // Mark all as uncolored initially

            int[] remainingVertices = new int[adjacencyMatrix.length];
            Arrays.fill(remainingVertices, -1); // Initialize remaining vertices

            for (int vertex : vertices) {
                if (vertex == -1) break; // Stop if no more vertices to process

                vertexFound = false;
                for (int coloredVertex = 0; coloredVertex < currentColorGroup.length; coloredVertex++) {
                    if (currentColorGroup[coloredVertex] == -1) break; // No more colored vertices

                    // Check if there's an edge between current vertex and any in the current color group
                    if (adjacencyMatrix[vertex][currentColorGroup[coloredVertex]] == 1) {
                        vertexFound = true; // Edge found, hence cannot color this vertex now
                        break;
                    }
                }

                if (!vertexFound) { // If no conflicting edge found, add vertex to current color group
                    int positionToAdd = findPositionToAdd(currentColorGroup);
                    currentColorGroup[positionToAdd] = vertex;
                } else { // Vertex cannot be colored in this round, add to remaining
                    int positionToAdd = findPositionToAdd(remainingVertices);
                    remainingVertices[positionToAdd] = vertex;
                }
            }

            vertices = remainingVertices; // Update vertices with remaining vertices for next iteration
            for (int coloredVertex : currentColorGroup) {
                if (coloredVertex == -1) break; // Stop appending if no more colored vertices
                coloringResult.append(coloredVertex).append(',');
            }
            coloringResult.append('/'); // Separate color groups
        }

        return coloringResult.toString();
    }

    private static int findPositionToAdd(int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == -1) {
                return i; // Returns the first position available to add a new element
            }
        }
        return -1; // Indicates array is full, should not happen in this context
    }

    public static String[] schedule(String raw_schedule) {
        String [] arr = names.toString().split("!");
        String[] data = raw_schedule.split("/");
        int cnt = 1;
        for (String s1: data) {
            String [] s2 = s1.split(",");
            String r = "";
            for (String c: s2) {
                if (!c.equals("-1")) {
                    int i = Integer.parseInt(c);
                    r += arr[i]+" ";
                }
            }
            cnt++;
        }
        int recnt = 0;
        int num = 1;
        String[] result  = new String[cnt-1];
        for (String s1: data) {
            String [] s2 = s1.split(",");
            String r = "";
            for (String c: s2) {
                if (!c.equals("-1")) {
                    int i = Integer.parseInt(c);
                    r += arr[i]+" ";
                }
            }
            result[recnt] = "Final Exam Period "+num+" => "+r;
            recnt++;
            num++;
        }
        return result;
    }
    public static String graphFormat(int[] graphLine) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < graphLine.length; i++) {
            sb.append(graphLine[i]);
        }
        return sb.toString();
    }

}