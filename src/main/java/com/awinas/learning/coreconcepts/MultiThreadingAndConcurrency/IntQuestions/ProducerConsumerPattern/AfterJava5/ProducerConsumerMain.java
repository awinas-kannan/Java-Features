package com.awinas.learning.coreconcepts.MultiThreadingAndConcurrency.IntQuestions.ProducerConsumerPattern.AfterJava5;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/*
 * Update the Thread Sleep to See Consumer Waiting (500) / Producer Waiting
 */
public class ProducerConsumerMain {

    public static void main(String[] args) {

        BlockingQueue<String> queue = new ArrayBlockingQueue<>(5);

        new Thread(new Producer(queue)).start();

        new Thread(new Consumer(queue)).start();
    }
}