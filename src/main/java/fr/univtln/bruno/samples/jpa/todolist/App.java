package fr.univtln.bruno.samples.jpa.todolist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import fr.univtln.bruno.samples.jpa.todolist.daos.ToDoDAO;
import fr.univtln.bruno.samples.jpa.todolist.entities.Tabular;
import fr.univtln.bruno.samples.jpa.todolist.entities.Task;
import fr.univtln.bruno.samples.jpa.todolist.entities.User;
import fr.univtln.bruno.samples.jpa.todolist.entities.ids.SimpleUser;
import fr.univtln.bruno.samples.jpa.todolist.entities.ids.SimpleUserAuto;
import fr.univtln.bruno.samples.jpa.todolist.entities.ids.embedded.EmbeddedUserID;
import fr.univtln.bruno.samples.jpa.todolist.entities.ids.embedded.UserWithEmbeddedID;
import fr.univtln.bruno.samples.jpa.todolist.entities.ids.idclass.UserWithIdClass;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import lombok.extern.java.Log;

import java.io.IOException;
import java.time.LocalDate;

@Log
public class App {
    public static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("todo_pu");

    public static void main(String[] args) {
        log.info("TodoList JPA started");

        EntityManager entityManager = EMF.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(SimpleUser.of(1, "John", "Doe"));
        entityManager.persist(SimpleUser.of(2, "Jane", "Doe"));

        entityManager.persist(UserWithIdClass.builder()
                .groupId(1)
                .userId(1)
                .firstname("John")
                .lastname("Doe")
                .build());
        entityManager.persist(UserWithIdClass.builder()
                .groupId(1)
                .userId(2)
                .firstname("Jane")
                .lastname("Doe")
                .build());

        entityManager.persist(UserWithEmbeddedID.builder()
                .embeddedUserID(EmbeddedUserID.of(1, 1))
                .firstname("John")
                .lastname("Doe")
                .build());
        entityManager.persist(UserWithEmbeddedID.builder()
                .embeddedUserID(EmbeddedUserID.of(1, 2))
                .firstname("Jane")
                .lastname("Doe")
                .build());

        entityManager.persist(SimpleUserAuto.builder().firstname("John").lastname("Doe").build());
        entityManager.persist(SimpleUserAuto.builder().firstname("Jane").lastname("Doe").build());

        User user1 = User.newInstance("Paul", "Smith").build();
        User user2 = User.newInstance("Jim", "Johnson").build();
        User user3 = User.newInstance("Jack", "Brown").build();
        User user4 = User.newInstance("Tim", "Miller").build();

        Tabular tabularTodo = Tabular.newInstance("todo").build();

        Task task1 = Task.newInstance(tabularTodo, user1, "First")
                .dueDate(LocalDate.now().plusDays(1))
                .description("My first task")
                .collaborator(user2)
                .collaborator(user3)
                .build();

        Task task2 = Task.newInstance(tabularTodo, user1, "Second")
                .collaborator(user2)
                .collaborator(user3)
                .dueDate(LocalDate.now().plusDays(3))
                .description("My second task")
                .build();

        Task task3 = Task.newInstance(tabularTodo, user4, "task4.1")
                .collaborator(user1)
                .collaborator(user2)
                .collaborator(user3)
                .dueDate(LocalDate.now().plusDays(3))
                .description("A task for U4")
                .build();

        entityManager.persist(task1);
        entityManager.persist(task2);
        entityManager.persist(task3);


        entityManager.flush();

        entityManager.refresh(tabularTodo);

        transaction.commit();

        tabularTodo = entityManager.find(Tabular.class, tabularTodo.getId());

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        try {
            log.info(mapper.writeValueAsString(tabularTodo));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        entityManager.close();

        EntityManager entityManager1 = EMF.createEntityManager();
        ToDoDAO toDoDAO = ToDoDAO.of(entityManager1);

        log.info("Tabulars: "+toDoDAO.findAllTabular().size());
        log.info("Tasks: "+toDoDAO.findAllTask().size());
        log.info("Users: "+toDoDAO.findAllUser().size());
        log.info("User1 owned tasks: "+toDoDAO.findTaskByOwner(user1).size());
        log.info("User1 collaborator tasks: "+toDoDAO.findTaskByCollaborator(user1).size());
        log.info("User1 tasks: "+toDoDAO.findTaskByUser(user1).size());
        log.info("USER 1:"+user1);
        log.info("User1 by UUID:"+toDoDAO.findUserbyUUID(user1.getUuid()));
        log.info("Task1 by UUID:"+toDoDAO.findTaskbyUUID(task1.getUuid()));

        entityManager1.close();
        EMF.close();
    }

}
