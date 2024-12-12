public class Stack {

    private Node front;

    public Stack() {
        front = null;
    }

    public void push(Node node) {
        if (front == null) {
            front = node;
        } else {
            node.next = front;
            front = node;
        }
    }

    public Node pop() {
        if (front == null)
            return null;

        Node temp = front;
        front = front.next;
        return temp;
    }

    public boolean isEmpty() {
        return front == null;
    }
}
