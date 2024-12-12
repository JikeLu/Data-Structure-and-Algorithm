/**
 * Author: Jikelu
 * ID: jikelu
 */
public class RedBlackTree {
    Node root;
    private Node nil;
    private int size;
    private int compareCount;
    private int id;
    /**
     *  It creates a RedBlackNode that represents null.
     *  It sets the internal variable tree to point to this
     *  node. This node that an empty tree points to will be
     *  used as a sentinel node. That is, all pointers in the
     *  tree that would normally contain the value null, will
     *  instead point to this sentinel node.
     */
    public RedBlackTree() {
        nil = new Node(-1,"nil",0, nil,nil,nil);
//        nil.setColor(0);
        root = nil;
//        root.setP(nil);
        id = 0;
    }
    /**
     * Pre-condition: memory is available for insertion
     * The insert() method places a data item into the tree.
     * @param value
     */
    public void insert(String value) {
        Node z = new Node(id++, value, 1, nil, nil, nil);
        Node y = nil;
        Node x = root;

        while (x != nil) {
            y = x;
            x = value.compareTo(x.getData()) < 0 ? x.getLeftChild() : x.getRightChild();
        }

        z.setParent(y);

        if (y == nil) {
            root = z;
        } else if (value.compareTo(y.getData()) < 0) {
            y.setLeftChild(z);
        } else {
            y.setRightChild(z);
        }

        z.setLeftChild(nil);
        z.setRightChild(nil);
        RBInsertFixup(z);
        size++;
    }
    /**
     * Pre-condition: right[x] != nil[T]
     * Pre-condition: root's parent is nill[T]
     * leftRotate() performs a single left rotation.
     * This would normally be a private method.
     * It executes the following algorithm from CLR.
     * @param x
     */
    public void leftRotate(Node x) {
        Node y = x.getRightChild();
        x.setRightChild(y.getLeftChild());
        if (y.getLeftChild() != nil) {
            y.getLeftChild().setParent(x);
        }
        y.setParent(x.getParent());
        if (x.getParent() == nil) {
            root = y;
        } else if (x == x.getParent().getLeftChild()) {
            x.getParent().setLeftChild(y);
        } else {
            x.getParent().setRightChild(y);
        }
        y.setLeftChild(x);
        x.setParent(y);
    }
    /**
     * Pre-condition: left[x] != nil[T]
     * Pre-condition: root's parent is nill[T]
     * rightRotate() performs a single right rotation This would normally be a private method.
     * It executes the following algorithm from CLR.
     * @param x
     */
    public void rightRotate(Node x) {
        Node y = x.getLeftChild();
        x.setLeftChild(y.getRightChild());
        if (y.getRightChild() != nil) {
            y.getRightChild().setParent(x);
        }
        y.setParent(x.getParent());
        if (x.getParent() == nil) {
            root = y;
        } else if (x == x.getParent().getLeftChild()) {
            x.getParent().setLeftChild(y);
        } else {
            x.getParent().setRightChild(y);
        }
        y.setRightChild(x);
        x.setParent(y);
    }
    /**
     * Fixing up the tree so that Red Black Properties are preserved. This would normally be a private method.
     * @param z - is the new node.
     */
    public void RBInsertFixup(Node z) {
        while (z.getParent().getColor() == 1) {
            if (z.getParent() == z.getParent().getParent().getLeftChild()) {
                Node y = z.getParent().getParent().getRightChild();
                if (y.getColor() == 1) {
                    z.getParent().setColor(0);
                    y.setColor(0);
                    z.getParent().getParent().setColor(1);
                    z = z.getParent().getParent();
                } else {
                    if (z == z.getParent().getRightChild()) {
                        z = z.getParent();
                        leftRotate(z);
                    }
                    z.getParent().setColor(0);
                    z.getParent().getParent().setColor(1);
                    rightRotate(z.getParent().getParent());
                }
            } else {
                Node y = z.getParent().getParent().getLeftChild();
                if (y.getColor() == 1) {
                    z.getParent().setColor(0);
                    y.setColor(0);
                    z.getParent().getParent().setColor(1);
                    z = z.getParent().getParent();
                } else {
                    if (z == z.getParent().getLeftChild()) {
                        z = z.getParent();
                        rightRotate(z);
                    }
                    z.getParent().setColor(0);
                    z.getParent().getParent().setColor(1);
                    leftRotate(z.getParent().getParent());
                }
            }
        }
        root.setColor(0);
    }
    /**
     *
     * @return number of values inserted into the tree.
     */
    public int getSize() {
        return size;
    }
    /**
     * Perfrom an inorder traversal of the tree. The inOrderTraversal(RedBlackNode) method is recursive and displays the content of the tree. It makes calls on System.out.println(). This method would normally be private.
     * @param t - the root of the tree on the first call.
     */
    public void inOrderTraversal(Node t) {
        if (t != null) {
            inOrderTraversal(t.getLeftChild());
            System.out.println(t);
            inOrderTraversal(t.getRightChild());
        }
    }
    /**
     * The no argument inOrderTraversal() method calls the recursive inOrderTraversal(RedBlackNode) - passing the root.
     */
    public void inOrderTraversal() {
        inOrderTraversal(root);
    }
    /**
     * reference: https://www.geeksforgeeks.org/reverse-level-order-traversal/
     * Perform a reverseOrder traversal of the tree.
     * The reverseOrderTraversal(RedBlackNode) method is recursive and displays the content of the tree in reverse order.
     * This method would normally be private.
     * @param t
     */
    public void reverseOrderTraversal(Node t,int level) {
        if (t == null)
            return;
        if (level == 1)
            System.out.print(t.getData() + " ");
        else if (level > 1)
        {
            reverseOrderTraversal(t.getLeftChild(), level - 1);
            reverseOrderTraversal(t.getRightChild(), level - 1);
        }
    }
    /**
     * The no argument reverseOrderTraversal() method calls the recursive reverseOrderTraversal(RedBlackNode) - passing the root.
     */
    public void reverseOrderTraversal() {
        int h = height(root);
        int i;
        for (i = h; i >= 1; i--)
        {
            reverseOrderTraversal(root, i);
        }
    }
    /**
     * The boolean contains() returns true if the String v is in the RedBlackTree and false otherwise.
     * It counts each comparison it makes in the variable recentCompares.
     * @param value - the value to search for.
     * @return true if v is in the tree, false otherwise;
     */
    public boolean contains(java.lang.String value) {
        Node y = nil;
        Node x = root;
        if (x == nil) {
            return false;
        }
        while (x != nil && !x.getData().equals(value)) {
            y = x;
            if (value.compareTo(x.getData()) < 0) {
                x = x.getLeftChild();
            } else {
                x = x.getRightChild();
            }
            if (x == nil) { // not found
                return false;
            }
        }
        return true;
    }
    /**
     * @return number of comparisons made in last call on the contains method.
     */
    public int getRecentCompares() {
        return compareCount;
    }
    /**
     * This method calls the recursive method.
     * @return the height of the red black tree.
     */
    public int height() {
        return height(root);
    }
    /**
     * reference: https://stackoverflow.com/questions/2597637/finding-height-in-binary-search-tree
     * This a recursive routine that is used to compute the height of the red black tree.
     * It is called by the height() method.
     * The height() method passes the root of the tree to this method. This method would normally be private.
     * @param t - a pointer to a node in the tree.
     * @return the height of node t.
     */
    public int height(Node t) {
        if (t == null) {
            return -1;
        }

        int lefth = height(t.getLeftChild());
        int righth = height(t.getRightChild());

        if (lefth > righth) {
            return lefth + 1;
        } else {
            return righth + 1;
        }
    }
    public int getID(String courseName) {
        Node y = nil;
        Node x = root;
        if (x == nil) {
            return -1;
        }
        while (x != nil && !x.getData().equals(courseName)) {
            y = x;
            if (courseName.compareTo(x.getData()) < 0) {

                x = x.getLeftChild();
            } else {
                compareCount++;
                x = x.getRightChild();
            }
            if (x == nil) { // not found
                return -1;
            }
        }
        return x.getCourseID();
    }

    @Override
    public String toString() {
        return toString (root);
    }
    private String toString(Node root) {
        StringBuilder builder = new StringBuilder();
        if (root == null)
            return "";
        builder.append(toString(root.getLeftChild()));
        builder.append(toString(root.getRightChild()));
        return builder.append(root.getData()).append(",").append(root.getCourseID()).append(" ").toString();
    }
}