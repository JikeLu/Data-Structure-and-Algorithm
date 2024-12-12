public class Queue {

    private Node front;
    private Node rear;

    public Queue() {
        front = null;
        rear = null;
    }

    public void enqueue(Node node) {
        if (front == null) {
            front = node;
            rear = node;
        } else {
            rear.next = node;
            node.prev = rear;
            rear = node;
        }
    }

    public Node dequeue() {
        if (front == null)
            return null;

        Node temp = front;
        front = front.next;
        if (front != null)
            front.prev = null;
        else
            rear = null;
        return temp;
    }

    public boolean isEmpty() {
        return front == null;
    }
}
