package edu.nuaa.H;

import java.util.HashMap;
import java.util.Map;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/16 11:04
 */
public class LRUCache {
    class Node {
        private Integer key;
        private Integer value;
        Node prev;
        Node next;

        public Node() {
        }

        public Node(Integer key, Integer value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node dummy;
    private Integer capacity;
    private Map<Integer, Node> position;
    public LRUCache(int capacity) {
        dummy = new Node(-1, -1);
        this.capacity = capacity;
        position = new HashMap<>();
        dummy.next = dummy;
        dummy.prev = dummy;
    }

    public int get(int key) {
        Node cur = getNode(key);
        return cur == null ? -1 : cur.value;
    }

    public void put(int key, int value) {
        Node cur = getNode(key);
        if (cur != null){
            cur.value = value;
            return;
        }
        Node newNode = new Node(key, value);
        position.put(key, newNode);
        addToHead(newNode);
        if (position.size() > capacity){
            Node tail = dummy.prev;
            remove(tail);
            position.remove(tail.key);
        }
    }

    public Node getNode(Integer cur){
        if (!position.containsKey(cur)){
            return null;
        }
        Node node = position.get(cur);
        remove(node);
        addToHead(node);
        return node;
    }

    public void addToHead(Node cur){
        cur.next = dummy.next;
        dummy.next.prev = cur;
        dummy.next = cur;
        cur.prev = dummy;
    }
    public void remove(Node cur){
        cur.prev.next = cur.next;
        cur.next.prev=  cur.prev;
        cur.next = null;
        cur.prev = null;
    }
}
