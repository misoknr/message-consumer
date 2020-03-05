package io.bootiq.consumer;

import io.bootiq.consumer.processor.Message;
import io.bootiq.consumer.processor.MessageProcessor;
import io.bootiq.consumer.processor.result.Result;
import lombok.Getter;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Consumer implements Runnable {

    public static final int RESULT_QUEUE_SIZE = 1000;

    private final BlockingQueue<Message> queue;
    @Getter
    private final BlockingQueue<Result> resultQueue = new ArrayBlockingQueue<>(RESULT_QUEUE_SIZE);
    private final MessageProcessor processor = new MessageProcessor();

    private AtomicBoolean running = new AtomicBoolean(false);
    private Thread worker;

    public Consumer(BlockingQueue<Message> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        running.set(true);

        try {
            while (running.get()) {
                consume(queue.take());
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void start() {
        worker = new Thread(this);
        worker.start();
    }

    public void stop() {
        running.set(false);
    }

    private void consume(Message m) {
        System.out.println("Consuming message: " + m);

        Result result = processor.processMessage(m);
        resultQueue.add(result);
    }

}
