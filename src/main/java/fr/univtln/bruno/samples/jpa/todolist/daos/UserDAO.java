package fr.univtln.bruno.samples.jpa.todolist.daos;

import fr.univtln.bruno.samples.jpa.todolist.entities.User;
import jakarta.persistence.EntityManager;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.util.List;
import java.util.UUID;

@Log
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDAO extends DAO<User> {
    public static UserDAO of(EntityManager entityManager) {
        return new UserDAO(entityManager);
    }
    private UserDAO(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    List<User> findAll() {
        return entityManager.createNamedQuery("user.findAll", User.class).getResultList();
    }

    @Override
    User findByUUID(UUID uuid) {
        return entityManager.createNamedQuery("user.findbyUUID", User.class).setParameter("uuid",uuid).getSingleResult();
    }
}
