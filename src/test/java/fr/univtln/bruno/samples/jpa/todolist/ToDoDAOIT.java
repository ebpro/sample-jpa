package fr.univtln.bruno.samples.jpa.todolist;

/*-
 * #%L
 * JPA Utils
 * %%
 * Copyright (C) 2020 - 2021 Université de Toulon
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

import fr.univtln.bruno.samples.jpa.todolist.daos.ToDoDAO;
import fr.univtln.bruno.samples.jpa.todolist.entities.Tabular;
import fr.univtln.bruno.samples.jpa.todolist.entities.Task;
import fr.univtln.bruno.samples.jpa.todolist.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import lombok.extern.java.Log;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type Abstract dao test.
 */
@Log
class ToDoDAOIT {

    private static EntityManagerFactory TEST_EMF;

    final Tabular tabularTodo = Tabular.newInstance("todo").build();
    Tabular tabularDoing = Tabular.newInstance("doing").build();
    Tabular tabularDone = Tabular.newInstance("done").build();

    final List<User> users = Arrays.asList(User.newInstance("Paul", "Smith").build(),
            User.newInstance("Jim", "Johnson").build(),
            User.newInstance("Jack", "Brown").build(),
            User.newInstance("Tim", "Miller").build());

    List<Task> tasks = Arrays.asList(
            Task.newInstance(tabularTodo, users.get(0), "task1.1")
                    .dueDate(LocalDate.now().plusDays(1))
                    .description("My first task")
                    .collaborators(Arrays.asList(users.get(1), users.get(2)))
                    .build(),
            Task.newInstance(tabularTodo, users.get(0), "task1.2")
                    .collaborators(Arrays.asList(users.get(1), users.get(3)))
                    .dueDate(LocalDate.now().plusDays(3))
                    .description("My second task")
                    .build(),
            Task.newInstance(tabularTodo, users.get(3), "task4.1")
                    .collaborators(Arrays.asList(users.get(0), users.get(1), users.get(2)))
                    .dueDate(LocalDate.now().plusDays(3))
                    .description("A task for U4")
                    .build());
    private EntityManager entityManager;
    private EntityTransaction transaction;

    /**
     * Init.
     */
    @BeforeAll
    public static void init() {
        TEST_EMF = Persistence.createEntityManagerFactory("test-pu");
    }

    @AfterAll
    public static void end() {
        TEST_EMF.close();
    }

    /**
     * Make new database.
     */
    @BeforeEach
    public void makeNewDatabase() {
        //Cleans up in memory database
        entityManager = TEST_EMF.createEntityManager();
        transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.createQuery("DELETE from Task").executeUpdate();
        entityManager.createQuery("DELETE from User").executeUpdate();
        entityManager.createQuery("DELETE from Tabular").executeUpdate();
        transaction.commit();


        entityManager = TEST_EMF.createEntityManager();
        //Prepare transaction
        transaction = entityManager.getTransaction();
        transaction.begin();
    }

    @AfterEach
    public void cleanAfterTest() {
        entityManager.close();
    }

    @Test
    void testAddTabular() {

        ToDoDAO toDoDAO = ToDoDAO.of(entityManager);

        toDoDAO.addTabular(tabularTodo);

        transaction.commit();

        List<Tabular> tabularList = toDoDAO.findAllTabular();

        assertEquals(1, tabularList.size());
        assertEquals(tabularTodo.getTasks().size(), toDoDAO.findAllTask().size());
        assertThat(tabularList.get(0), samePropertyValuesAs(tabularTodo));
    }

    @Test
    void testAddTask() {

        ToDoDAO toDoDAO = ToDoDAO.of(entityManager);

        Tabular tabular1 = Tabular.newInstance("tabular1").build();
        User user1 = User.newInstance("John", "Doe").build();
        User user2 = User.newInstance("William", "Smith").build();
        User user3 = User.newInstance("Mary", "Roberts").build();
        Task task1 = Task.newInstance(tabular1, user1, "task1")
                .collaborator(user2)
                .collaborator(user3)
                .build();

        toDoDAO.addTask(task1);

        transaction.commit();

        List<Task> taskList = toDoDAO.findAllTask();
        assertEquals(1, taskList.size());
        assertEquals(1, toDoDAO.findAllTabular().size());
        assertEquals(3, toDoDAO.findAllUser().size());
        assertThat(taskList.get(0), samePropertyValuesAs(task1));
    }

    @Test
    void testAddUser() {
        ToDoDAO toDoDAO = ToDoDAO.of(entityManager);

        User user1 = User.newInstance("John", "Doe").build();

        toDoDAO.addUser(user1);

        transaction.commit();

        List<User> userList = toDoDAO.findAllUser();

        assertEquals(1, userList.size());
        assertThat(user1, samePropertyValuesAs(user1));
    }

}
