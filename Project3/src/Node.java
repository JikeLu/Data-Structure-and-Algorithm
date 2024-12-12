/**
 * Author: Jikelu
 * ID: jikelu
 */
public class Node {
    public static final int BLACK = 0; // https://www.andrew.cmu.edu/user/mm6/95-771/examples/DSARedBlackTreeProject/dist/javadoc/constant-values.html#redblacktreetesterproject.RedBlackNode.RED
    public static final int RED = 1;
    //    private static java.lang.String data;
    private  int courseID;
    private  java.lang.String courseName;
    private  int color;
    private  Node parent, leftChild, rightChild;

    /**
     *
     * @param data a simple value held in the tree
     * @param color either RED or BLACK
     * @param p the parent pointer
     * @param lc the pointer to the left child (will be null only for the node that represents all external nulls.
     * @param rc the pointer to the right child (will be null only for the node that represents all external nulls.
     */
    public Node(int id,java.lang.String data, int color, Node parent, Node leftChild, Node rightChild) {
        this.courseID = id;
        this.courseName = data;
        this.color = color;
        this.parent = parent;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public  String getData() {
        return this.courseName;
    }

    public  void setData(String data) {
        this.courseName = data;
    }

    public  int getColor() {
        return color;
    }

    public  void setColor(int color) {
        this.color = color;
    }

    public Node getParent() {
        return parent;
    }

    public  void setParent(Node parent) {
        this.parent = parent;
    }

    public  Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public  Node getRightChild() {
        return rightChild;
    }

    public  void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    public  int getCourseID() {
        return courseID;
    }

    @Override
    public String toString() {
        return "RedBlackNode{"+courseName+","+courseID+"}";
    }
}