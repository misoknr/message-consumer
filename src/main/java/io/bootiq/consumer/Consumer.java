package io.bootiq.consumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Consumer implements Runnable {

    private final BlockingQueue<Message> queue;
    private final MessageProcessor processor = new MessageProcessor();

    private AtomicBoolean running= new AtomicBoolean(false);
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

        processor.processMessage(m);
    }

}
