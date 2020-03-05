package io.bootiq.consumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class App {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Message> queue = new ArrayBlockingQueue<>(1000);
        Consumer consumer = new Consumer(queue);
        Thread t = new Thread(consumer);
        t.start();

        queue.add(new Message(Message.Instruction.CREATE, "jozko"));
        queue.add(new Message(Message.Instruction.CREATE, "ferko"));
        queue.add(new Message(Message.Instruction.LIST));
        queue.add(new Message(Message.Instruction.DELETE_ALL));

//        Thread.sleep(5000);
//
//        System.out.println("Interrupting consumer thread");
//        t.interrupt();
    }

}
