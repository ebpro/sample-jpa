package fr.univtln.bruno.samples.jpa.todolist.daos;

import fr.univtln.bruno.samples.jpa.todolist.entities.Tabular;
import fr.univtln.bruno.samples.jpa.todolist.entities.Task;
import fr.univtln.bruno.samples.jpa.todolist.entities.User;
import jakarta.persistence.EntityManager;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.UUID;

@Log
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ToDoDAO {
    final EntityManager entityManager;

    final TabularDAO tabularDAO;
    final TaskDAO taskDAO;
    final UserDAO userDAO;

    private PropertyChangeSupport support;

    private ToDoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.tabularDAO = TabularDAO.of(entityManager);
        this.taskDAO = TaskDAO.of(entityManager);
        this.userDAO = UserDAO.of(entityManager);
    }

    public static ToDoDAO of(EntityManager entityManager) {
        return new ToDoDAO(entityManager);
    }

    public List<Task> findTaskByState(Task.State state) {
        return taskDAO.findByState(state);
    }

    public List<Task> findOpenedTasks() {
        return findTaskByState(Task.State.OPENED);
    }

    public List<Task> findClosedTasks() {
        return findTaskByState(Task.State.CLOSED);
    }

    public List<Task> findTaskByOwner(User owner) {
        return taskDAO.findByOwner(owner);
    }

    public List<Task> findTaskByCollaborator(User collaborator) {
        return taskDAO.findByCollaborator(collaborator);
    }

    public List<Task> findTaskByUser(User user) {
        return taskDAO.findByUser(user);
    }

    public List<Tabular> findAllTabular() {
        return tabularDAO.findAll();
    }

    public List<Task> findAllTask() {
        return taskDAO.findAll();
    }

    public List<User> findAllUser() {
        return userDAO.findAll();
    }

    public Tabular findTabularByUUID(UUID uuid) {
        return tabularDAO.findByUUID(uuid);
    }

    public Task findTaskbyUUID(UUID uuid) {
        return taskDAO.findByUUID(uuid);
    }

    public User findUserbyUUID(UUID uuid) {
        return entityManager.createNamedQuery("user.findbyUUID", User.class).setParameter("uuid", uuid).getSingleResult();
    }

    public void addUser(User user) {
        userDAO.persist(user);
    }

    public void addTask(Task task) {
        taskDAO.persist(task);
    }

    public void addTabular(Tabular tabular) {
        tabularDAO.persist(tabular);
    }

    public void removeTabular(Tabular tabular) {
        tabularDAO.remove(tabular);
    }

    public void removeTask(Task task) {
        taskDAO.remove(task);
    }

    public void removeUser(User user) {
        userDAO.remove(user);
    }

    public void removeTabular(UUID tabular) {
        tabularDAO.remove(tabular);
    }

    public void removeTask(UUID task) {
        taskDAO.remove(task);
    }

    public void removeUser(UUID user) {
        userDAO.remove(user);
    }
}
