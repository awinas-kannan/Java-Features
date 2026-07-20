package com.awinas.learning.aa_tests.LRU;

public class Node {

    Integer key;
    Integer value;

    Node prev;
    Node next;

    Node(Integer key, Integer value) {
        this.key = key;
        this.value = value;
        this.prev = null;
        this.next = null;
    }

}
