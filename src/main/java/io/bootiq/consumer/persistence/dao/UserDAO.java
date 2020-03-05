package io.bootiq.consumer.persistence.dao;

import io.bootiq.consumer.persistence.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class UserDAO {

    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("io.bootiq.consumer");

    public void createUser(User user) throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(user);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<User> listAll() throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        List<User> result = entityManager.createQuery("select u from User u", User.class).getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();

        return result;
    }

    public int deleteAll() throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        int entitiesDeleted = entityManager.createQuery("delete from User").executeUpdate();

        entityManager.getTransaction().commit();
        entityManager.close();

        return entitiesDeleted;
    }

}
