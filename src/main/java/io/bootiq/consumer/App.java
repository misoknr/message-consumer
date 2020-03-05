package io.bootiq.consumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class App {

    public static final int QUEUE_SIZE = 100;

    public static void main(String[] args) {
        BlockingQueue<Message> queue = new ArrayBlockingQueue<>(QUEUE_SIZE);
        Consumer consumer = new Consumer(queue);
        consumer.start();
    }

}
