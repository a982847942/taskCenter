package edu.nuaa.wyhulianwang;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author brain
 * @version 1.0
 * @date 2024/9/21 19:31
 */
public class T03 {
    static class Node{
        String address;
        String data;
        String nextAddress;
        Node next;

        public Node(String address, String data, String nextAddress) {
            this.address = address;
            this.data = data;
            this.nextAddress = nextAddress;
        }

        public Node() {
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Integer headAddress_1 = sc.nextInt();
        int N = sc.nextInt();
        int K = sc.nextInt();
        Map<String, Node> position = new HashMap<>();
        for (int i = 0; i < N; i++) {
            String address = sc.next();
            String data = sc.next();
            String nextAddress = sc.next();
            Node node = new Node(address, data, nextAddress);
            position.put(address, node);
        }
        StringBuilder headAddress_2 = new StringBuilder(Integer.toString(headAddress_1));
        int diff = 5 -headAddress_2.length();
        char[] temp = new char[5];
        for (int i = 0; i < diff; i++) {
            temp[i] = '0';
        }
        for (int i = diff; i < 5; i++) {
            temp[i] = headAddress_2.charAt(i - diff);
        }
        String headAddress = new String(temp);
//        System.out.println(headAddress);
        Node head = position.get(headAddress);
        String nextAddress = head.nextAddress;
        Node cur = head;
        while (!nextAddress.equals("-1")) {
            Node node = position.get(nextAddress);
            cur.next = node;
            cur = node;
            nextAddress = cur.nextAddress;
        }
        cur.next = null;
        // k个翻转
        Node newHead = reverseK(head, K);
        cur = newHead;
        while (cur != null){
            System.out.println(cur.address + " " + cur.data + " " + cur.nextAddress);
            cur = cur.next;
        }
    }

    public static Node reverseK(Node head, int k){
        if (head == null || head.next == null || k == 1)return head;
        Node dummy = new Node("-1" ,"-1" ,"-1");
        dummy.next = head;
        Node pre = dummy;
        Node end = pre;
        while (end != null){
            for (int i = 0; i < k && end != null; i++) {
                end = end.next;
            }
            if (end == null)break;
            Node next = end.next;
            end.next = null;
            Node start = pre.next;
            pre.next = reverse(start);
            start.next = next;
            pre = start;
            end = pre;
        }
        return dummy.next;
    }
    public static Node reverse(Node head){
        Node pre = null;
        Node cur = head;
        while (cur != null){
            Node next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }
}
