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
import java.util.Arrays;

@Log
public class App {
    public final static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("todo_pu");

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

        User user1 = User.builder("Paul", "Smith").build();
        User user2 = User.builder("Jim", "Johnson").build();
        User user3 = User.builder("Jack", "Brown").build();
        User user4 = User.builder("Tim", "Miller").build();


        Tabular tabular1 = Tabular.builder()
                .title("todo").build();

        Task task1 = Task.builder(tabular1, user1, "task1.1")
                .dueDate(LocalDate.now().plusDays(1))
                .description("My first task")
                .collaborators(Arrays.asList(new User[]{user2, user3}))
                .build();

        Task task2 = Task.builder(tabular1, user1, "task1.2")
                .collaborators(Arrays.asList(new User[]{user2, user3}))
                .dueDate(LocalDate.now().plusDays(3))
                .description("My second task")
                .build();

        Task task3 = Task.builder(tabular1, user4, "task4.1")
                .collaborators(Arrays.asList(new User[]{user1, user2, user3}))
                .dueDate(LocalDate.now().plusDays(3))
                .description("A task for U4")
                .build();

        entityManager.persist(task1);
        entityManager.persist(task2);
        entityManager.persist(task3);


        entityManager.flush();

        entityManager.refresh(tabular1);

        transaction.commit();

        //entityManager.refresh(task1);
        tabular1 = entityManager.find(Tabular.class, tabular1.getId());

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        try {
            // mapper.writeValue(System.out, new User[]{user1,user2,user3,user4});
            mapper.writeValue(System.out, tabular1);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        //log.info(task1.toString());

        entityManager.close();

        ToDoDAO toDoDAO = ToDoDAO.newInstance();

        log.info("Tabulars: "+toDoDAO.findAllTabulars().size());
        log.info("Tasks: "+toDoDAO.findAllTasks().size());
        log.info("Users: "+toDoDAO.findAllUsers().size());
        log.info("User1 owned tasks: "+toDoDAO.findTaskOwnedByUser(user1).size());
        log.info("User1 collaborator tasks: "+toDoDAO.findTaskCollaboratorByUser(user1).size());
        log.info("User1 tasks: "+toDoDAO.findTaskByUser(user1).size());
        log.info("USER 1:"+user1);
        log.info("User1 by UUID:"+toDoDAO.findUserbyUUID(user1.getUuid()));

    }

}
