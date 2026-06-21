package com.awinas.learning.coreconcepts.MultiThreadingAndConcurrency.IntQuestions.ProducerConsumerPattern.BeforeJava5;

class Consumer implements Runnable {

    private final SharedBuffer buffer;

    public Consumer(SharedBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {

        try {
            while (true) {

                buffer.consume();

                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}