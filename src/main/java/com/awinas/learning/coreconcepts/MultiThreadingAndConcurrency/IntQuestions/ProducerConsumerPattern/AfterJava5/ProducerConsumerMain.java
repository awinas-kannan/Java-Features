package com.awinas.learning.coreconcepts.MultiThreadingAndConcurrency.IntQuestions.ProducerConsumerPattern.AfterJava5;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumerMain {

    public static void main(String[] args) {

        BlockingQueue<String> queue = new ArrayBlockingQueue<>(5);

        new Thread(new Producer(queue)).start();

        new Thread(new Consumer(queue)).start();
    }
}