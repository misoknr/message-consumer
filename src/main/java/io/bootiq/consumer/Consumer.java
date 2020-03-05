package io.bootiq.consumer;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {

    private final BlockingQueue<Message> queue;
    private final MessageProcessor processor = new MessageProcessor();

    public Consumer(BlockingQueue<Message> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                consume(queue.take());
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private void consume(Message m) {
        System.out.println("Consuming message: " + m);

        processor.processMessage(m);
    }

}
