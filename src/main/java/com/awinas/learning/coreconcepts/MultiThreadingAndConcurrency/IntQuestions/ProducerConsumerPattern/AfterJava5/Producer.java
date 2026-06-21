package com.awinas.learning.coreconcepts.MultiThreadingAndConcurrency.IntQuestions.ProducerConsumerPattern.AfterJava5;

import java.util.concurrent.BlockingQueue;

class Producer implements Runnable {

    private BlockingQueue<String> queue;

    public Producer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        try {
            int i = 1;

            while (true) {

                String item = "Order-" + i++;

                // Check if queue is full
                if (queue.remainingCapacity() == 0) {
                    System.out.println(
                            "Producer waiting. Queue is FULL."
                    );
                }

                queue.put(item);

                System.out.println(
                        "Produced : " + item +
                                " | Queue Size : " + queue.size()
                );

                Thread.sleep(1000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}