/**
 * Jike Lu, jikelu
 * 95-771 Assignment1
 */

package edu.colorado.nodes;

public class ObjectNode
{
    // Invariant of the ObjectNode class:
    //   1. The node's Object data is in the instance variable data.
    //   2. For the final node of a list, the link part is null.
    //      Otherwise, the link part is a reference to the
    //      next node of the list.
    private Object data;
    private ObjectNode link;


    /**
     * Initialize a node with a specified initial data and link to the next
     * node. Note that the initialLink may be the null reference,
     * which indicates that the new node has nothing after it.
     * @param initialData
     *   the initial data of this new node
     * @param initialLink
     *   a reference to the node after this new node--this reference may be null
     *   to indicate that there is no node after this new node.
     * @postcondition
     *   This node contains the specified data and link to the next node.
     **/
    public ObjectNode(Object initialData, ObjectNode initialLink)
    {
        data = initialData;
        link = initialLink;
    } //O(1)


    /**
     * Modification method to add a new node after this node.
     * @param item
     *   the data to place in the new node
     * @postcondition
     *   A new node has been created and placed after this node.
     *   The data for the new node is item. Any other nodes
     *   that used to be after this node are now after the new node.
     * @exception OutOfMemoryError
     *   Indicates that there is insufficient memory for a new
     *   ObjectNode.
     **/
    public void addNodeAfter(Object item)
    {
        link = new ObjectNode(item, link);
    } //O(1)


    /**
     * Accessor method to get the data from this node.
     * @return
     *   the data from this node
     **/
    public Object getData( )
    {
        return data;
    } //O(1)


    /**
     * Accessor method to get a reference to the next node after this node.
     * @return
     *   a reference to the node after this node (or the null reference if there
     *   is nothing after this node)
     **/
    public ObjectNode getLink( )
    {
        return link;
    } //O(1)


    /**
     * Copy a list.
     * @param source
     *   the head of a linked list that will be copied (which may be
     *   an empty list in where source is null)
     * @return
     *   The method has made a copy of the linked list starting at
     *   source. The return value is the head reference for the
     *   copy.
     * @exception OutOfMemoryError
     *   Indicates that there is insufficient memory for the new list.
     **/
    //Precondition: source.data must not be null.
    public static ObjectNode listCopy(ObjectNode source)
    {
        ObjectNode copyHead;
        ObjectNode copyTail;

        // Handle the special case of the empty list.
        if (source == null)
            return null;

        // Make the first node for the newly created list.
        copyHead = new ObjectNode(source.data, null);
        copyTail = copyHead;

        // Make the rest of the nodes for the newly created list.
        while (source.link != null)
        {
            source = source.link;
            copyTail.addNodeAfter(source.data);
            copyTail = copyTail.link;
        }

        // Return the head reference for the new list.
        return copyHead;
    } //O(N)


    /**
     * Copy a list, returning both a head and tail reference for the copy.
     * @param source
     *   the head of a linked list that will be copied (which may be
     *   an empty list in where source is null)
     * @return
     *   The method has made a copy of the linked list starting at
     *   source.  The return value is an
     *   array where the [0] element is a head reference for the copy and the [1]
     *   element is a tail reference for the copy.
     * @exception OutOfMemoryError
     *   Indicates that there is insufficient memory for the new list.
     **/
    public static ObjectNode[ ] listCopyWithTail(ObjectNode source)
    {
        ObjectNode copyHead;
        ObjectNode copyTail;
        ObjectNode[ ] answer = new ObjectNode[2];

        // Handle the special case of the empty list.
        if (source == null)
            return answer; // The answer has two null references .

        // Make the first node for the newly created list.
        copyHead = new ObjectNode(source.data, null);
        copyTail = copyHead;

        // Make the rest of the nodes for the newly created list.
        while (source.link != null)
        {
            source = source.link;
            copyTail.addNodeAfter(source.data);
            copyTail = copyTail.link;
        }

        // Return the head and tail references.
        answer[0] = copyHead;
        answer[1] = copyTail;
        return answer;
    } //O(N)


    /**
     * Compute the number of nodes in a linked list.
     * @param head
     *   the head reference for a linked list (which may be an empty list
     *   with a null head)
     * @return
     *   the number of nodes in the list with the given head
     * @note
     *   A wrong answer occurs for lists longer than Int.MAX_VALUE.
     **/
    public static int listLength(ObjectNode head)
    {
        ObjectNode cursor;
        int answer;

        answer = 0;
        for (cursor = head; cursor != null; cursor = cursor.link)
            answer++;

        return answer;
    } //O(N)


    /**
     * Copy part of a list, providing a head and tail reference for the new copy.
     * @precondition
     *   start and end are non-null references to nodes
     *   on the same linked list,
     *   with the start node at or before the end node.
     * @return
     *   The method has made a copy of the part of a linked list, from the
     *   specified start node to the specified end node. The return value is an
     *   array where the [0] component is a head reference for the copy and the
     *   [1] component is a tail reference for the copy.
     * @param start
     *   first node to copy
     * @param end
     *   final node to copy
     * @exception IllegalArgumentException
     *   Indicates that start and end are not references
     *   to nodes on the same list.
     * @exception NullPointerException
     *   Indicates that start is null.
     * @exception OutOfMemoryError
     *   Indicates that there is insufficient memory for the new list.
     **/
    public static ObjectNode[ ] listPart(ObjectNode start, ObjectNode end)
    {
        ObjectNode copyHead;
        ObjectNode copyTail;
        ObjectNode cursor;
        ObjectNode[ ] answer = new ObjectNode[2];

        // Make the first node for the newly created list. Notice that this will
        // cause a NullPointerException if start is null.
        copyHead = new ObjectNode(start.data, null);
        copyTail = copyHead;
        cursor = start;

        // Make the rest of the nodes for the newly created list.
        while (cursor != end)
        {
            cursor = cursor.link;
            if (cursor == null)
                throw new IllegalArgumentException
                        ("end node was not found on the list");
            copyTail.addNodeAfter(cursor.data);
            copyTail = copyTail.link;
        }

        // Return the head and tail references
        answer[0] = copyHead;
        answer[1] = copyTail;
        return answer;
    } //O(N)


    /**
     * Find a node at a specified position in a linked list.
     * @param head
     *   the head reference for a linked list (which may be an empty list in
     *   which case the head is null)
     * @param position
     *   a node number
     * @precondition
     *   position &gt; 0.
     * @return
     *   The return value is a reference to the node at the specified position in
     *   the list. (The head node is position 1, the next node is position 2, and
     *   so on.) If there is no such position (because the list is too short),
     *   then the null reference is returned.
     * @exception IllegalArgumentException
     *   Indicates that position is not positive.
     **/
    public static ObjectNode listPosition(ObjectNode head, int position)
    {
        ObjectNode cursor;
        int i;

        if (position <= 0)
            throw new IllegalArgumentException("position is not positive");

        cursor = head;
        for (i = 1; (i < position) && (cursor != null); i++)
            cursor = cursor.link;

        return cursor;
    } //O(N)


    /**
     * Search for a particular piece of data in a linked list.
     * @param head
     *   the head reference for a linked list (which may be an empty list in
     *   which case the head is null)
     * @param target
     *   a piece of data to search for
     * @return
     *   The return value is a reference to the first node that contains the
     *   specified target. If there is no such node, the null reference is
     *   returned.
     **/
    public static ObjectNode listSearch(ObjectNode head, Object target)
    {
        ObjectNode cursor;

        for (cursor = head; cursor != null; cursor = cursor.link)
            if (target == cursor.data)
                return cursor;

        return null;
    } //O(N)


    /**
     * Modification method to remove the node after this node.
     * @precondition
     *   This node must not be the tail node of the list.
     * @postcondition
     *   The node after this node has been removed from the linked list.
     *   If there were further nodes after that one, they are still
     *   present on the list.
     * @exception NullPointerException
     *   Indicates that this was the tail node of the list, so there is nothing
     *   after it to remove.
     **/
    public void removeNodeAfter( )
    {
        link = link.link;
    } //O(1)


    /**
     * Modification method to set the data in this node.
     * @param newData
     *   the new data to place in this node
     * @postcondition
     *   The data of this node has been set to newData.
     **/
    public void setData(Object newData)
    {
        data = newData;
    } //O(1)


    /**
     * Modification method to set the link to the next node after this node.
     * @param newLink
     *   a reference to the node that should appear after this node in the linked
     *   list (or the null reference if there is no node after this node)
     * @postcondition
     *   The link to the node after this node has been set to newLink.
     *   Any other node (that used to be in this link) is no longer connected to
     *   this node.
     **/
    public void setLink(ObjectNode newLink)
    {
        link = newLink;
    } //O(1)

    public static ObjectNode listCopy_rec(ObjectNode source) {
        // Base case: handle the special case of the empty list.
        if (source == null)
            return null;

        // Recursive case: make the first node for the newly created list.
        ObjectNode copyHead = new ObjectNode(source.data, null);

        // Recursive case: copy the rest of the nodes for the newly created list.
        copyHead.link = listCopy_rec(source.link);

        // Return the head reference for the new list.
        return copyHead;
    } //O(N)

    public static int listLength_rec(ObjectNode head) {
        // Base case: handle the special case of an empty list.
        if (head == null)
            return 0;

        // Recursive case: increment the length and move to the next node.
        return 1 + listLength_rec(head.link);
    } //O(N)

    public static void displayEveryThird(ObjectNode head) {
        // Start counting from the first node
        int count = 1;
        ObjectNode cursor = head;

        while (cursor != null) {
            // Display the data of every third node
            if (count % 3 == 0) {
                System.out.println(cursor.data);
            }

            // Move to the next node
            cursor = cursor.link;
            count++;
        }
    } //O(N)

    public String toString() {
        StringBuilder result = new StringBuilder();
        ObjectNode current = this; // Start from the head

        while (current != null) {
            result.append(current.data).append(" "); // Append the data of the current node
            current = current.link; // Move to the next node
        }

        return result.toString().trim(); // Trim to remove trailing space and return as a string
    }

    public static void main(String[] args) {
        // a. Create a list containing 26 nodes with letters of the English alphabet.
        ObjectNode front = createAlphabetList();

        // b. Display the list using toString().
        System.out.println(front.toString());

        // c. Display every third node using displayEveryThird().
        ObjectNode.displayEveryThird(front);

        // d. Print the size of the list using listLength() and listLength_rec().
        System.out.println("Number of nodes = " + ObjectNode.listLength(front));
        System.out.println("Number of nodes = " + ObjectNode.listLength_rec(front));

        // e. Make a copy of the list using listCopy().
        ObjectNode k = ObjectNode.listCopy(front);

        // f. Display the copied list using toString().
        System.out.println(k.toString());

        // g. Print the size of the copied list using listLength() and listLength_rec().
        System.out.println("Number of nodes in k = " + ObjectNode.listLength(k));
        System.out.println("Number of nodes in k = " + ObjectNode.listLength_rec(k));

        // h. Make a copy of the list using listCopy_rec().
        ObjectNode k2 = ObjectNode.listCopy_rec(front);

        // i. Display the recursively copied list using toString().
        System.out.println(k2.toString());

        // j. Print the size of the recursively copied list using listLength() and listLength_rec().
        System.out.println("Number of nodes in k2 = " + ObjectNode.listLength(k2));
        System.out.println("Number of nodes in k2 = " + ObjectNode.listLength_rec(k2));
    }

    // Helper method to create a list containing 26 nodes with letters of the English alphabet.
    private static ObjectNode createAlphabetList() {
        ObjectNode front = new ObjectNode('a', null);
        ObjectNode current = front;

        for (char letter = 'b'; letter <= 'z'; letter++) {
            current.addNodeAfter(letter);
            current = current.getLink();
        }

        return front;
    }
}
           