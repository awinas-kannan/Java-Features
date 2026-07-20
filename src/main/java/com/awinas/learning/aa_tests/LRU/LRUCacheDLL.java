package com.awinas.learning.aa_tests.LRU;

import java.util.HashMap;
import java.util.Map;

public class LRUCacheDLL {

    Integer capacity;
    Node head;
    Node tail;

    Map<Integer, Node> cacheMap = new HashMap<>();

    public LRUCacheDLL(int capacity) {
        this.capacity = capacity;
        this.head = new Node(0, 0);
        this.tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
    }

    public Integer get(Integer key) {

        if (cacheMap.containsKey(key)) {
            Node temp = cacheMap.get(key);

            remove(temp);
            insert(new Node(temp.key, temp.value));

            return temp.value;
        }

        return -1;
    }

    public void put(Integer key, Integer value) {

        if (cacheMap.containsKey(key)) {
            // Update the value
            // Move to tail
            Node temp = cacheMap.get(key);
            remove(temp);
            insert(new Node(temp.key, value));
            return;
        }

        if (cacheMap.size() == capacity) {
            // remove recently used from head
            Node temp = head.next;
            remove(temp);
        }
        // add new node at the last
        insert(new Node(key, value));

    }

    // 0 ---  1 2 3 4 5 --- 0
    // 3
    // 1 2 4 5
    public void remove(Node node) {
        Node prev = node.prev;
        Node next = node.next;

        prev.next = next;
        next.prev = prev;
    }

    // Move to tail.
    public void insert(Node node) {
        Node temp = tail.prev;
        temp.next = node;
        tail.prev = node;
    }

}
