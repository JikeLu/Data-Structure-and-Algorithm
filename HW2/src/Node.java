public class Node {
    CrimeData data;
    Node left;
    Node right;
    Node next; // For Queue and Stack
    Node prev; // For Queue

    // Constructor for TwoDTree
    public Node(CrimeData data) {
        this.data = data;
        this.prev = null;
        this.next = null;
        this.left = null;
        this.right = null;
    }

    // Constructor for Queue and Stack
    public Node(CrimeData data, Node next, Node prev) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }
}
