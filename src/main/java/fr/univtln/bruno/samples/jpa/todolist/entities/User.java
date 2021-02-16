package fr.univtln.bruno.samples.jpa.todolist.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Log
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED) //needed by JPA
@ToString
@EqualsAndHashCode(of = "uuid")
@Getter

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "uuid")

@NamedQueries({
        @NamedQuery(name = "user.findAll", query = "select user from User user"),
        @NamedQuery(name = "user.findbyUUID", query = "select user from User user where user.uuid = :uuid")
})

@Entity
@Table(name = "USER")
public class User implements Serializable {
    @Id
    @GeneratedValue
    //@SequenceGenerator(name="user_seq")
    //@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_seq")
    @Column(name = "USER_ID")
    @JsonIgnore
    long id;

    @Column(name = "UUID", unique = true, updatable = false, nullable = false)
    UUID uuid;

    @Column(name = "FIRSTNAME", nullable = false)
    String firstname;

    @Column(name = "LASTNAME", nullable = false)
    String lastname;

    @ToString.Exclude
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "owner", cascade = {CascadeType.ALL})
    @OrderBy("dueDate DESC")
    Set<Task> ownedTasks;

    @ToString.Exclude
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToMany(mappedBy = "collaborators", cascade = {CascadeType.ALL})
    Set<Task> asCollaboratorTasks;

    @Builder(builderMethodName = "newInstance")
    private User(String firstname, String lastname, @Singular Set<Task> ownedTasks, @Singular Set<Task> asCollaboratorTasks) {
        this.uuid = UUID.randomUUID();
        this.firstname = firstname;
        this.lastname = lastname;
        this.ownedTasks = new HashSet<>(ownedTasks);
        ownedTasks.forEach(task -> task.setOwner(this));
        this.asCollaboratorTasks = new HashSet<>(asCollaboratorTasks);
        asCollaboratorTasks.forEach(task -> task.getCollaborators().add(this));
    }

    public static User.UserBuilder newInstance(String firstname, String lastname) {
        return new UserBuilder().firstname(firstname).lastname(lastname);
    }

}
