package com.awinas.learning.coreconcepts.MultiThreadingAndConcurrency.IntQuestions.ProducerConsumerPattern.BeforeJava5;

import java.util.LinkedList;
import java.util.Queue;

class SharedBuffer {

    private final Queue<String> queue = new LinkedList<>();
    private final int capacity;

    public SharedBuffer(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void produce(String item)
            throws InterruptedException {

        while (queue.size() == capacity) {
            System.out.println(
                    "Producer waiting. Queue is FULL.");
            wait();
        }

        queue.add(item);

        System.out.println(
                "Produced : " + item +
                        " | Queue Size : " + queue.size());

        notifyAll();
    }

    public synchronized String consume()
            throws InterruptedException {

        while (queue.isEmpty()) {
            System.out.println(
                    "Consumer waiting. Queue is EMPTY.");
            wait();
        }

        String item = queue.poll();

        System.out.println(
                "Consumed : " + item +
                        " | Queue Size : " + queue.size());

        notifyAll();

        return item;
    }
}