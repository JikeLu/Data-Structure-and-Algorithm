/**
 * Jike Lu, jikelu
 * 95-771 Assignment1
 */

package edu.cmu.andrew.jikelu;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MerkleTree {
    private SinglyLinkedList contents = new SinglyLinkedList();
    private String root;

    public static void main(String[] args){
        MerkleTree tree = new MerkleTree();
        try {
            // CrimeLatLonXY is the file with the output
            tree.readFile("CrimeLatLonXY.csv");
            tree.hashToRoot();
            System.out.println(tree.root);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    public static String h(String text) throws
            NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash =
                digest.digest(text.getBytes(StandardCharsets.UTF_8));
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i <= 31; i++) {
            byte b = hash[i];
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    // Read the file and create the SLL
    public void readFile(String filename) throws IOException {
        BufferedReader input = null;
        SinglyLinkedList list0 = new SinglyLinkedList();
        try {
            File f = new File(filename);
            input = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF8"));
            String line;
            while((line = input.readLine()) != null){
                list0.addAtEndNode(line);
            }
            // Check if adding a node is needed
            list0 = ifOdd(list0);
            list0.reset();
            SinglyLinkedList list1 = new SinglyLinkedList();
            while(list0.hasNext()){
                list1.addAtEndNode(h((String) list0.next()));
            }
            contents.addAtEndNode(list0);
            contents.addAtEndNode(list1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                input.close();
            }
        }
    }

    public void hashToRoot() throws NoSuchAlgorithmException {
        contents.reset();
        contents.next();
        SinglyLinkedList tmpList = (SinglyLinkedList) contents.next();
        root = recursiveHash(tmpList);
    }

    private String recursiveHash(SinglyLinkedList list) throws NoSuchAlgorithmException {
        if (list.countNodes() < 2) {
            // base case: list has less than 2 nodes, return the root node
            list.reset();
            return (String) list.next();
        }

        SinglyLinkedList leaf = new SinglyLinkedList();

        // hash list and store it in toAdd
        list.reset();
        while (list.hasNext()) {
            leaf.addAtEndNode(h(list.next() + (String) list.next()));
        }

        if (leaf.countNodes() > 1 && leaf.countNodes() % 2 != 0) {
            leaf = ifOdd(leaf);
        }

        contents.addAtEndNode(leaf);

        // recursive call with the new list
        return recursiveHash(leaf);
    }


    private SinglyLinkedList ifOdd(SinglyLinkedList list) {
        int count = 0;
        String lastNode = null;

        // Traverse the list and count nodes
        list.reset();
        while (list.hasNext()) {
            lastNode = (String) list.next();
            count++;
        }

        // If the number of nodes is odd, add the last node again
        if (count % 2 == 1) {
            list.addAtEndNode(lastNode);
        }

        return list;
    }


}