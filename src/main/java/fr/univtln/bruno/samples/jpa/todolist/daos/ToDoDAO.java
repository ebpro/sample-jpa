package fr.univtln.bruno.samples.jpa.todolist.daos;

import fr.univtln.bruno.samples.jpa.todolist.App;
import fr.univtln.bruno.samples.jpa.todolist.entities.Tabular;
import fr.univtln.bruno.samples.jpa.todolist.entities.Task;
import fr.univtln.bruno.samples.jpa.todolist.entities.User;
import jakarta.persistence.EntityManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.util.List;
import java.util.UUID;

@Log
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(staticName = "newInstance")
public class ToDoDAO {
    EntityManager entityManager = App.EMF.createEntityManager();

    public List<Tabular> findAllTabulars() {
        return entityManager.createNamedQuery("tabular.findAll", Tabular.class).getResultList();
    }
    public Tabular findTabularByUUID(UUID uuid) {
        return entityManager.createNamedQuery("tabular.findbyUUID", Tabular.class).setParameter("uuid",uuid).getSingleResult();
    }

    public List<Task> findAllTasks() {
        return entityManager.createNamedQuery("task.findAll", Task.class).getResultList();
    }
    public Task findTaskbyUUID(UUID uuid) {
        return entityManager.createNamedQuery("task.findbyUUID", Task.class).setParameter("uuid",uuid).getSingleResult();
    }
    public List<Task> findTaskByStatus(Task.State state) {
        return entityManager.createNamedQuery("task.findbyState", Task.class).setParameter("state",state).getResultList();
    }
    public List<Task> findOpenedTasks() {
        return findTaskByStatus(Task.State.OPENED);
    }
    public List<Task> findClosedTasks() {
        return findTaskByStatus(Task.State.CLOSED);
    }
    public List<Task> findTaskOwnedByUser(User user) {
        return entityManager.createNamedQuery("task.findbyOwner", Task.class).setParameter("user",user).getResultList();
    }
    public List<Task> findTaskCollaboratorByUser(User user) {
        return entityManager.createNamedQuery("task.findbyCollaborator", Task.class).setParameter("user",user).getResultList();
    }
    public List<Task> findTaskByUser(User user) {
        return entityManager.createNamedQuery("task.findbyUser", Task.class).setParameter("user",user).getResultList();
    }


    public List<User> findAllUsers() {
        return entityManager.createNamedQuery("user.findAll", User.class).getResultList();
    }
    public User findUserbyUUID(UUID uuid) {
        return entityManager.createNamedQuery("user.findbyUUID", User.class).setParameter("uuid",uuid).getSingleResult();
    }

}
