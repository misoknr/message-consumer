package io.bootiq;

import io.bootiq.consumer.Consumer;
import io.bootiq.consumer.enums.Instruction;
import io.bootiq.consumer.processor.Message;
import io.bootiq.consumer.persistence.entity.User;
import io.bootiq.consumer.processor.result.DeleteUsersResult;
import io.bootiq.consumer.processor.result.ListUsersResult;
import junit.framework.TestCase;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ConsumerTest extends TestCase {

    private EntityManagerFactory entityManagerFactory;

    @Override
    protected void setUp() throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory("io.bootiq.consumer");
    }

    @Override
    protected void tearDown() throws Exception {
        entityManagerFactory.close();
    }

    @Test
    public void testDatabase() {
        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();
        List<User> userList = em.createQuery("select u from User u").getResultList();
        em.getTransaction().commit();

        assertEquals(0, userList.size());

        em.getTransaction().begin();
        em.persist(new User(1l, "a1", "Dusan"));
        em.persist(new User(2l, "a2", "Fero"));
        userList = em.createQuery("select u from User u").getResultList();
        em.getTransaction().commit();

        assertEquals(2, userList.size());

        em.getTransaction().begin();
        em.createQuery("delete from User").executeUpdate();
        userList = em.createQuery("select u from User u").getResultList();
        em.getTransaction().commit();

        assertEquals(0, userList.size());

        em.close();
    }

    @Test
    public void testConsumer() throws InterruptedException {
        EntityManager em = entityManagerFactory.createEntityManager();
        BlockingQueue<Message> queue = new ArrayBlockingQueue(10);
        Consumer c = new Consumer(queue);
        c.start();

        // Add messages for user creation
        queue.add(new Message(Instruction.CREATE, new User(1l, "a1", "Dusan")));
        queue.add(new Message(Instruction.CREATE, new User(2l, "a2", "Fero")));

        c.getResultQueue().poll(1l, TimeUnit.SECONDS);
        c.getResultQueue().poll(1l, TimeUnit.SECONDS);

        // Add LIST instruction to queue
        queue.add(new Message(Instruction.LIST));

        ListUsersResult listResult = (ListUsersResult) c.getResultQueue().poll(1l, TimeUnit.SECONDS);

        assertEquals(Instruction.LIST, listResult.getMessageType());
        assertEquals(2, listResult.getList().size());

        // Add DELETE_ALL instruction to queue following by LIST instruction
        queue.add(new Message(Instruction.DELETE_ALL));
        queue.add(new Message(Instruction.LIST));

        DeleteUsersResult deleteResult = (DeleteUsersResult) c.getResultQueue().poll(1l, TimeUnit.SECONDS);
        assertEquals(Instruction.DELETE_ALL, deleteResult.getMessageType());

        listResult = (ListUsersResult) c.getResultQueue().poll(2l, TimeUnit.SECONDS);
        assertEquals(0, listResult.getList().size());

        c.stop();
    }

}
