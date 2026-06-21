package com.awinas.learning.coreconcepts.MultiThreadingAndConcurrency.IntQuestions.ProducerConsumerPattern.AfterJava5;

import java.util.concurrent.BlockingQueue;

class Consumer implements Runnable {

    private BlockingQueue<String> queue;

    public Consumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        try {

            while (true) {

                // Check if queue is empty
                if (queue.isEmpty()) {
                    System.out.println(
                            "Consumer waiting. Queue is EMPTY."
                    );
                }

                String item = queue.take();

                System.out.println(
                        "Consumed : " + item +
                                " | Queue Size : " + queue.size()
                );

                Thread.sleep(2000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}