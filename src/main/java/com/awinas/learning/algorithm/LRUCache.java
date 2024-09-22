package com.awinas.learning.algorithm;
import java.util.HashMap;

class LRUCache {

    // Doubly Linked List Node
    class Node {
        int key, value;
        Node prev, next;
        
        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private int capacity;
    private HashMap<Integer, Node> cache;
    private Node head, tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        // Initialize dummy head and tail nodes to avoid null pointer issues
        head = new Node(0, 0);
        tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
    }

    // Get the value of the key if it exists in the cache, otherwise return -1
    public int get(int key) {
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            if (node != null) {
                remove(node);  // Remove the node from its current position
                insert(node);  // Insert the node at the front (most recently used)
                return node.value;
            }
        }
        return -1;  // Key not found in cache
    }

    // Add or update a key-value pair in the cache
    public void put(int key, int value) {
        if (cache.containsKey(key)) {
            Node existingNode = cache.get(key);
            if (existingNode != null) {
                remove(existingNode); // Remove the old node if the key is already present
            }
        }
        if (cache.size() == capacity) {
            if (tail.prev != null) {
                remove(tail.prev);  // Remove least recently used node (before the tail)
            }
        }
        insert(new Node(key, value)); // Insert the new node at the front
    }

    // Remove a node from the doubly linked list and HashMap
    private void remove(Node node) {
        if (node == null) return;  // Safeguard against null node
        cache.remove(node.key);    // Remove from HashMap

        // Null checks to avoid NullPointerException when relinking nodes
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
    }

    // Insert a node at the front (most recently used) of the doubly linked list
    private void insert(Node node) {
        if (node == null) return;  // Safeguard against null node
        cache.put(node.key, node); // Add to HashMap

        // Insert node after head
        node.next = head.next;
        node.prev = head;
        if (head.next != null) {
            head.next.prev = node;
        }
        head.next = node;
    }

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2); // Capacity of 2

        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.get(1)); // Returns 1
        cache.put(3, 3);                  // Evicts key 2
        System.out.println(cache.get(2)); // Returns -1 (not found)
        cache.put(4, 4);                  // Evicts key 1
        System.out.println(cache.get(1)); // Returns -1 (not found)
        System.out.println(cache.get(3)); // Returns 3
        System.out.println(cache.get(4)); // Returns 4
    }
}
