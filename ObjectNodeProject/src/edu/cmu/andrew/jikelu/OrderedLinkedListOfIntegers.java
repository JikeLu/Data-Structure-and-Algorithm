/**
 * Jike Lu, jikelu
 * 95-771 Assignment1
 */

package edu.cmu.andrew.jikelu;

import java.util.Random;

public class OrderedLinkedListOfIntegers extends SinglyLinkedList {
    private class IntNode {
        int data;
        IntNode next;

        public IntNode(int data, IntNode next) {
            this.data = data;
            this.next = next;
        }
    }

    protected IntNode head;
    public void sortedAdd(int value) {
        IntNode newNode = new IntNode(value, null);

        if (head == null || value < head.data) {
            newNode.next = head;
            head = newNode;
        } else {
            IntNode current = head;
            while (current.next != null && current.next.data < value) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
        }
    }

    public static OrderedLinkedListOfIntegers merge(OrderedLinkedListOfIntegers list1, OrderedLinkedListOfIntegers list2) {
        // Preconditions: Both lists are sorted in increasing order.

        OrderedLinkedListOfIntegers mergedList = new OrderedLinkedListOfIntegers();
        IntNode current1 = list1.head;
        IntNode current2 = list2.head;

        while (current1 != null && current2 != null) {
            if (current1.data < current2.data) {
                mergedList.sortedAdd(current1.data);
                current1 = current1.next;
            } else {
                mergedList.sortedAdd(current2.data);
                current2 = current2.next;
            }
        }

        // Add remaining elements from list1, if any
        while (current1 != null) {
            mergedList.sortedAdd(current1.data);
            current1 = current1.next;
        }

        // Add remaining elements from list2, if any
        while (current2 != null) {
            mergedList.sortedAdd(current2.data);
            current2 = current2.next;
        }

        return mergedList;
    }

    public void displayList() {
        IntNode current = head;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Random rand = new Random();

        OrderedLinkedListOfIntegers list1 = new OrderedLinkedListOfIntegers();
        OrderedLinkedListOfIntegers list2 = new OrderedLinkedListOfIntegers();

        // Adding 20 random values to list1 and list2
        for (int i = 0; i < 20; i++) {
            list1.sortedAdd(rand.nextInt(100));
            list2.sortedAdd(rand.nextInt(100));
        }

        System.out.println("List 1:");
        list1.displayList();

        System.out.println("List 2:");
        list2.displayList();

        // Merging list1 and list2
        OrderedLinkedListOfIntegers mergedList = OrderedLinkedListOfIntegers.merge(list1, list2);

        System.out.println("Merged List:");
        mergedList.displayList();
    }
}