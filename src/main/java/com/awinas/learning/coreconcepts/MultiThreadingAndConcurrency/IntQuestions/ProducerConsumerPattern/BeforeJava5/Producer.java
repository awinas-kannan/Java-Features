package com.awinas.learning.coreconcepts.MultiThreadingAndConcurrency.IntQuestions.ProducerConsumerPattern.BeforeJava5;

class Producer implements Runnable {

    private final SharedBuffer buffer;

    public Producer(SharedBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {

        int count = 1;

        try {
            while (true) {

                String item = "Order-" + count++;

                buffer.produce(item);

                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}