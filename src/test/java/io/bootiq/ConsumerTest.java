package io.bootiq;

import io.bootiq.consumer.Consumer;
import io.bootiq.consumer.Message;
import io.bootiq.consumer.persistence.entity.User;
import junit.framework.TestCase;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static io.bootiq.consumer.Message.Instruction.*;

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
    public void testConsumer() {
        EntityManager em = entityManagerFactory.createEntityManager();
        BlockingQueue queue = new ArrayBlockingQueue(10);
        Consumer c = new Consumer(queue);
        c.start();

        // Add messages for user creation
        queue.add(new Message(CREATE, new User(1l, "a1", "Dusan")));
        queue.add(new Message(CREATE, new User(2l, "a2", "Fero")));

        // Test that users were created
        em.getTransaction().begin();
        User user1 = (User) em.createQuery("select u from User u where u.id = 1").getSingleResult();
        User user2 = (User) em.createQuery("select u from User u where u.id = 2").getSingleResult();
        em.getTransaction().commit();

        assertEquals("Dusan", user1.getName());
        assertEquals("Fero", user2.getName());

        // Add LIST instruction to queue
        queue.add(new Message(LIST));

        // Test result size
        em.getTransaction().begin();
        List<User> userList = em.createQuery("select u from User u").getResultList();
        em.getTransaction().commit();

        assertEquals(2, userList.size());

        // Add DELETE_ALL instruction to queue following by LIST instruction
        queue.add(new Message(DELETE_ALL));
        queue.add(new Message(LIST));

        // Test result size
        em.getTransaction().begin();
        userList = em.createQuery("select u from User u").getResultList();
        em.getTransaction().commit();

        assertEquals(2, userList.size());

        c.stop();
    }

}
