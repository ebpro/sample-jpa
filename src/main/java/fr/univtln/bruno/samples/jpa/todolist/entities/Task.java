package fr.univtln.bruno.samples.jpa.todolist.entities;

import com.fasterxml.jackson.annotation.*;
import fr.univtln.bruno.samples.jpa.todolist.entities.listeners.TaskEntityListener;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Log
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED) //needed by JPA
@ToString
@EqualsAndHashCode
@Getter

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "uuid")

@Entity
@EntityListeners(TaskEntityListener.class)
@NamedQueries({
        @NamedQuery(name="task.findAll", query = "select task from Task task"),
        @NamedQuery(name="task.findbyUUID", query = "select task from Task task where task.uuid = :uuid"),
        @NamedQuery(name="task.findbyState", query = "select task from Task task where task.state = :state"),
        @NamedQuery(name="task.findbyOwner", query = "select task from Task task where task.owner = :user"),
        @NamedQuery(name="task.findbyCollaborator", query = "select task from Task task where :user member of task.collaborators"),
        @NamedQuery(name="task.findbyUser", query = "select task from Task task where task.owner = :user or :user member of task.collaborators")
})
public class Task implements Serializable {
    @Id
    @Column(updatable = false, nullable = false, name ="TASK_ID")
    @JsonIgnore
    long id;

    @Column(updatable = false, nullable = false, name = "UUID", unique = true)
    UUID uuid;

    @Column(nullable = false, name = "CREATION_TIME")
    final LocalTime creationTime = LocalTime.now();

    @Setter
    @Column(nullable = false, name = "UPDATE_TIME")
    LocalDateTime updateTime = LocalDateTime.now();

    @Setter
    @Column(name = "DUE_DATE")
    LocalDate dueDate;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "STATE")
    State state = State.OPENED;

    @Setter
    @Column(name="TITLE")
    String title;

    @OneToOne(orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "TASK_ID")
    //Could be use to map task Id to content ID
    // but Task Cntent MUST exist before Content.
    @MapsId
    TaskContent taskContent;

    @Setter
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "CREATOR_ID")
    @JsonIdentityReference(alwaysAsId = true)
    User owner;

    @Singular("User")
    @ManyToMany(cascade = {CascadeType.ALL})
    @JsonIdentityReference(alwaysAsId = true)
    @JoinTable(name = "TASK_COLLABORATOR",
            joinColumns = {@JoinColumn(name = "TASK_ID")},
            inverseJoinColumns = {@JoinColumn(name = "COLLABORATOR_ID")})
    Set<User> collaborators;

    @Setter
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "TABULAR_ID")
    Tabular tabular;

    @Builder(builderMethodName = "newInstance")
    private Task(Tabular tabular, User owner, String title, String description, LocalDate dueDate, @Singular Set<User> collaborators) {
        this.uuid = UUID.randomUUID();
        this.tabular = tabular;
        this.owner = owner;
        owner.getOwnedTasks().add(this);
        this.title = title;
        this.dueDate = dueDate;
        this.collaborators = new HashSet<>(collaborators);
        collaborators.forEach(user->user.getOwnedTasks().add(this));
        taskContent = TaskContent.builder().description(description).build();
    }

    @PreRemove
    private void removeAllUsers() {
        owner = null;
        collaborators.clear();
    }


    public static TaskBuilder newInstance(Tabular tabular, User owner, String title) {
        return new TaskBuilder().tabular(tabular).owner(owner).title(title);
    }

    public enum State {OPENED, CLOSED}
}
