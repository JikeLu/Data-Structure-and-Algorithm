/**
 * Jike Lu, jikelu
 * 95-771 Assignment1
 */

package edu.cmu.andrew.jikelu;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Scanner;

public class MerkleHellmanKnapsackCryptoProject {
    private final SinglyLinkedList w;
    private final SinglyLinkedList B;
    private String s;
    private BigInteger r;
    private BigInteger q;
    private BigInteger c;
    private Random random = new Random();

    public static void main(String[] args) {
        System.out.println("Enter your message, max 80 characters: ");
        Scanner s = new Scanner(System.in);
        String message = s.nextLine();
        while(message.length()>80){
            System.out.println("Message is too long. Try again: ");
            message = s.nextLine();
        }
        System.out.println("You entered: " + message);
        System.out.println("Number of clear text bytes = " + message.getBytes().length);
        MerkleHellmanKnapsackCryptoProject m = new MerkleHellmanKnapsackCryptoProject(message);
        System.out.println(message + " is encrypted as");
        System.out.println(m.encrypted());
        System.out.println("Result of decryption: " + m.decrypted());
    }

    public BigInteger greaterThanSum(SinglyLinkedList w) {
        BigInteger sum = BigInteger.ZERO;

        // Calculate the sum of the superincreasing sequence w
        w.reset();
        while (w.hasNext()) {
            BigInteger element = (BigInteger) w.next();
            sum = sum.add(element);
        }

        // Choose a random integer q such that q > sum(w)
        SecureRandom random = new SecureRandom();
        BigInteger q;
        do {
            q = new BigInteger(sum.bitLength(), random);
        } while (q.compareTo(sum) <= 0);

        return q;
    }

    public BigInteger generateRandomCoprime(BigInteger q) {
        SecureRandom random = new SecureRandom();
        BigInteger r;

        do {
            // Choose a random integer r
            r = new BigInteger(q.bitLength(), random);

            // Ensure that gcd(r, q) = 1
        } while (!r.gcd(q).equals(BigInteger.ONE));

        return r;
    }

    public SinglyLinkedList generatePublicKey(SinglyLinkedList w, BigInteger q, BigInteger r) {
        SinglyLinkedList b = new SinglyLinkedList();

        // Generate public key material b = (r * wi) mod q for each wi in w
        SecureRandom random = new SecureRandom();
        w.reset();
        while (w.hasNext()) {
            BigInteger wi = (BigInteger) w.next();
            BigInteger bi = r.multiply(wi).mod(q);
            b.addAtEndNode(bi);
        }

        return b;
    }
    public MerkleHellmanKnapsackCryptoProject(String input){
        s = input;
        //create the private key: superincreasing sequence w,
        w = new SinglyLinkedList();

        int n = s.getBytes().length * 8;
        BigInteger tmp = new BigInteger(n, random);
        BigInteger sum = BigInteger.ZERO;
        while(n != 0){
            sum = sum.add(tmp);
            w.addAtFrontNode(tmp);
            tmp = tmp.multiply(BigInteger.valueOf(7));
            n--;
        }
        //calculate the random bignumber q
        q = greaterThanSum(w);
        r = generateRandomCoprime(q);
        w.reset();
        //Create publicSequence with r and w
        B = generatePublicKey(w,q,r);
    }

    public String stringToBinary() {
        String msgBytes = new BigInteger(s.getBytes()).toString(2);
        if (msgBytes.length() < B.countNodes()) {
            msgBytes = String.format("%0" + (B.countNodes() - msgBytes.length()) + "d", 0) + msgBytes;
        };
        return msgBytes;
    }

    public String encrypted(){
        //c is the encrypted number
        c = BigInteger.ZERO;

        //convert input string to binary string
        String msgBytes = stringToBinary();

        //calculate c
        B.reset();
        for(int i = 0; i < msgBytes.length(); i++){
            BigInteger tmp = BigInteger.valueOf(Character.getNumericValue(msgBytes.charAt(i)));
            tmp = tmp.multiply((BigInteger) B.next());
            c = c.add(tmp);
        }
        return c.toString();
    }

    public String decryptedMessage(BigInteger tmp, byte[] outByte) {
        w.reset();
        int index = 0;
        while(w.hasNext()){
            BigInteger tmpBI = (BigInteger) w.next();
            if(tmp.compareTo(tmpBI) >= 0){
                outByte[index] = 1;
                tmp = tmp.subtract(tmpBI);
            } else {
                outByte[index] = 0;
            }
            index++;
        }
        //convert the result to string
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < outByte.length; i++) {
            sb.append(outByte[i]);
        }
        return sb.toString();
    }
    public String decrypted(){
        //convert c to the long number used to decrypted
        BigInteger tmp = c.multiply(r.modInverse(q)).mod(q);
        byte[] resultBinary = new byte[B.countNodes()];
        String decryptedMessage = decryptedMessage(tmp, resultBinary);

        return new String(new BigInteger(decryptedMessage, 2).toByteArray());

    }


}