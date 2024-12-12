import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TwoDTree {
    private Node root;

    public TwoDTree(String crimeDataLocation) {
        root = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(crimeDataLocation));
            // Skip the first line
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                double x = Double.parseDouble(parts[0]);
                double y = Double.parseDouble(parts[1]);
                int time = Integer.parseInt(parts[2]);
                String street = parts[3];
                String offense = parts[4];
                String date = parts[5];
                int tract = Integer.parseInt(parts[6]);
                double latitude = Double.parseDouble(parts[7]);
                double longitude = Double.parseDouble(parts[8]);
                insert(new CrimeData(x, y, time, street, offense, date, tract, latitude, longitude));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Node insertRec(Node root, CrimeData crimeData, int depth) {
        if (root == null) {
            return new Node(crimeData);
        }

        int cd = depth % 2;

        if (cd == 0) {
            if (crimeData.x < root.data.x)
                root.left = insertRec(root.left, crimeData, depth + 1);
            else
                root.right = insertRec(root.right, crimeData, depth + 1);
        } else {
            if (crimeData.y < root.data.y)
                root.left = insertRec(root.left, crimeData, depth + 1);
            else
                root.right = insertRec(root.right, crimeData, depth + 1);
        }

        return root;
    }

    public void insert(CrimeData crimeData) {
        root = insertRec(root, crimeData, 0);
    }

    public void preOrderPrint() {
        preOrderPrintRec(root);
    }

    private void preOrderPrintRec(Node root) {
        if (root != null) {
            System.out.println(root.data.x + ", " + root.data.y);
            preOrderPrintRec(root.left);
            preOrderPrintRec(root.right);
        }
    }

    public void inOrderPrint() {
        inOrderPrintRec(root);
    }

    private void inOrderPrintRec(Node root) {
        if (root != null) {
            inOrderPrintRec(root.left);
            System.out.println(root.data.x + ", " + root.data.y);
            inOrderPrintRec(root.right);
        }
    }

    public void postOrderPrint() {
        postOrderPrintRec(root);
    }

    private void postOrderPrintRec(Node root) {
        if (root != null) {
            postOrderPrintRec(root.left);
            postOrderPrintRec(root.right);
            System.out.println(root.data.x + ", " + root.data.y);
        }
    }

    public void levelOrderPrint() {
        if (root == null)
            return;

        Queue queue = new Queue();
        queue.enqueue(root);

        while (!queue.isEmpty()) {
            Node current = queue.dequeue();
            System.out.println(current.data.x + ", " + current.data.y);

            if (current.left != null)
                queue.enqueue(current.left);
            if (current.right != null)
                queue.enqueue(current.right);
        }
    }

    // The method is big Theta(N) where n is the number of nodes in the tree.
    // This is because every node in the tree is visited once during the traversal, and the time spent per node is constant.
    public void reverseLevelOrderPrint() {
        if (root == null)
            return;

        Queue queue = new Queue();
        Stack stack = new Stack();
        queue.enqueue(root);

        while (!queue.isEmpty()) {
            Node current = queue.dequeue();
            stack.push(current);

            if (current.right != null)
                queue.enqueue(current.right);
            if (current.left != null)
                queue.enqueue(current.left);
        }

        while (!stack.isEmpty()) {
            Node current = stack.pop();
            System.out.println(current.data.x + ", " + current.data.y);
        }
    }

    public ListOfCrimes findPointsInRange(double x1, double y1, double x2, double y2) {
        ListOfCrimes result = new ListOfCrimes();
        findPointsInRange(root, x1, y1, x2, y2, result);
        return result;
    }

    private void findPointsInRange(Node node, double x1, double y1, double x2, double y2, ListOfCrimes result) {
        if (node == null) {
            return;
        }

        // Check if node's crime record lies within the specified range
        if (node.data.getX() >= x1 && node.data.getX() <= x2 && node.data.getY() >= y1 && node.data.getY() <= y2) {
            result.addCrime(node.data); // Add crime record to result list
        }

        // Recursively search left/bottom subtree if applicable
        if (node.data.getX() >= x1) {
            findPointsInRange(node.left, x1, y1, x2, y2, result);
        }
        // Recursively search right/top subtree if applicable
        if (node.data.getX() <= x2) {
            findPointsInRange(node.right, x1, y1, x2, y2, result);
        }
    }

    public Neighbor nearestNeighbor(double x1, double y1) {
        if (root == null) {
            return null;
        }
        Neighbor nearest = new Neighbor(Double.MAX_VALUE, null);
        nearestNeighbor(root, x1, y1, nearest);
        return nearest;
    }

    private void nearestNeighbor(Node node, double x1, double y1, Neighbor nearest) {
        if (node == null) {
            return;
        }
        double distance = Math.sqrt(Math.pow(node.data.getX() - x1, 2) + Math.pow(node.data.getY() - y1, 2));
        if (distance < nearest.getDistance()) {
            nearest.distance = distance;
            nearest.nearestNode = node;
        }

        int cd = 0;
        if (x1 < node.data.getX()) {
            if (y1 < node.data.getY()) {
                nearestNeighbor(node.left, x1, y1, nearest);
                cd = 0;
            } else {
                nearestNeighbor(node.right, x1, y1, nearest);
                cd = 1;
            }
        } else {
            if (y1 < node.data.getY()) {
                nearestNeighbor(node.left, x1, y1, nearest);
                cd = 0;
            } else {
                nearestNeighbor(node.right, x1, y1, nearest);
                cd = 1;
            }
        }

        double diff;
        if (cd == 0) {
            diff = x1 - node.data.getX();
        } else {
            diff = y1 - node.data.getY();
        }

        if (Math.abs(diff) < nearest.getDistance()) {
            if (cd == 0) {
                nearestNeighbor(cd == 0 ? node.right : node.left, x1, y1, nearest);
            } else {
                nearestNeighbor(cd == 0 ? node.left : node.right, x1, y1, nearest);
            }
        }
    }
}
