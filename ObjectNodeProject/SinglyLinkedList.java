package edu.cmu.andrew.jikelu;

import java.math.BigInteger;
import java.util.Random;

public class SinglyLinkedList {

    private class Node {
        private Object data;
        private Node next;

        public Node(Object data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

    private Node head;
    private Node tail;
    private Node iterator;
    private int nodeCount;

    public SinglyLinkedList() {
        head = null;
        tail = null;
        iterator = null;
        nodeCount = 0;
    }

    public boolean hasNext() {
        return iterator != null;
    }

    public void addAtEndNode(Object obj) {
        Node newNode = new Node(obj, null);
        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        nodeCount++;
    }

    public void addAtFrontNode(BigInteger obj) {
        Node newNode = new Node(obj, head);
        head = newNode;
        if (tail == null) {
            tail = head;
        }
        nodeCount++;
    }

    public Object getLast() {
        if (tail == null) {
            return null;
        } else {
            return tail.data;
        }
    }

    // Add a method to reverse the list
    public void reverse() {
        Node prev = null;
        Node current = head;
        Node next;

        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }

        head = prev;
    }

    public int countNodes() {
        return nodeCount;
    }

    public Object getObjectAt(int i) {
        Node current = head;
        for (int j = 0; j < i; j++) {
            if (current == null) {
                return null;
            }
            current = current.next;
        }
        if (current == null) {
            return null;
        } else {
            return current.data;
        }
    }

    public Object next() {
        if (iterator == null) {
            return null;
        }
        Object data = iterator.data;
        iterator = iterator.next;
        return data;
    }

    public void reset() {
        iterator = head;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        Node current = head;

        while (current != null) {
            result.append(current.data).append(" ");
            current = current.next;
        }

        return result.toString().trim();
    }
}
