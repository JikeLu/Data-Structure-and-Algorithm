public class Neighbor {
    double distance;
    Node nearestNode;

    public Neighbor(double distance, Node nearestNode) {
        this.distance = distance;
        this.nearestNode = nearestNode;
    }

    public double getDistance() {
        return distance;
    }

    public Node getNearestNode() {
        return nearestNode;
    }
}
