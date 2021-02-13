package fr.univtln.bruno.samples.jpa.todolist.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Log
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode
@Getter

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "uuid")

@Entity
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
    @Column(updatable = false, nullable = false)
    @JsonIgnore
    long id;

    @Column(name = "UUID", unique = true)
    UUID uuid;

    @Column(name = "CREATION_TIME")
    final LocalTime creationTime = LocalTime.now();

    @Column(name = "UPDATE_TIME")
    LocalDateTime updateTime = LocalDateTime.now();

    @Column(name = "DUE_DATE")
    LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    State state = State.OPENED;

    @Setter
    String title;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "TASKCONTENT_ID")
    //Could be use to map task Id to content ID
    // but Task Cntent MUST exist before Content.
    @MapsId
    TaskContent taskContent;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "CREATOR_ID")
    @JsonIdentityReference(alwaysAsId = true)
    User owner;

    @Singular("User")
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    @JsonIdentityReference(alwaysAsId = true)
    @JoinTable(name = "TASK_COLLABORATOR",
            joinColumns = {@JoinColumn(name = "TASK_ID")},
            inverseJoinColumns = {@JoinColumn(name = "COLLABORATOR_ID")})
    List<User> collaborators = new ArrayList<>();

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "TABULAR_ID")
    Tabular tabular;

    @Builder
    private Task(Tabular tabular, User owner, String title, String description, LocalDate dueDate, List<User> collaborators) {
        this.uuid = UUID.randomUUID();
        this.tabular = tabular;
        this.owner = owner;
        this.title = title;
        this.dueDate = dueDate;
        this.collaborators = collaborators;
        taskContent = TaskContent.builder().description(description).build();
    }

    public static TaskBuilder builder(Tabular tabular, User owner, String title) {
        return new TaskBuilder().tabular(tabular).owner(owner).title(title);
    }

    public enum State {OPENED, CLOSED}
}
