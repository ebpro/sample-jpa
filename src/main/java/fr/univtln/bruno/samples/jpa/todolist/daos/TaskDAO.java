package fr.univtln.bruno.samples.jpa.todolist.daos;

import fr.univtln.bruno.samples.jpa.todolist.entities.Task;
import fr.univtln.bruno.samples.jpa.todolist.entities.User;
import jakarta.persistence.EntityManager;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.util.List;
import java.util.UUID;

@Log
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskDAO extends DAO<Task> {

    public static TaskDAO of(EntityManager entityManager) {
        return new TaskDAO(entityManager);
    }
    private TaskDAO(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    List<Task> findAll() {
        return entityManager.createNamedQuery("task.findAll", Task.class).getResultList();
    }

    @Override
    Task findByUUID(UUID uuid) {
        return entityManager.createNamedQuery("task.findbyUUID", Task.class).setParameter("uuid", uuid).getSingleResult();
    }

    List<Task> findByState(Task.State state) {
        return entityManager.createNamedQuery("task.findbyState", Task.class).setParameter("state", state).getResultList();
    }

    public List<Task> findByOwner(User user) {
        return entityManager.createNamedQuery("task.findbyOwner", Task.class).setParameter("user", user).getResultList();
    }

    public List<Task> findByCollaborator(User collaborator) {
        return entityManager.createNamedQuery("task.findbyCollaborator", Task.class).setParameter("user", collaborator).getResultList();
    }

    public List<Task> findByUser(User user) {
        return entityManager.createNamedQuery("task.findbyUser", Task.class).setParameter("user", user).getResultList();
    }

}
