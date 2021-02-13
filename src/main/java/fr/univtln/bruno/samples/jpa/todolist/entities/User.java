package fr.univtln.bruno.samples.jpa.todolist.entities;

import com.fasterxml.jackson.annotation.*;
import fr.univtln.bruno.samples.jpa.todolist.entities.listeners.MyEntityListener;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Log
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "uuid")

@NamedQueries({
        @NamedQuery(name="user.findAll", query = "select user from User user"),
        @NamedQuery(name="user.findbyUUID", query = "select user from User user where user.uuid = :uuid")
})

@Entity
@EntityListeners(MyEntityListener.class)
@Table(name = "USER")
public class User implements Serializable {
    @Id
    @GeneratedValue
    //@SequenceGenerator(name="user_seq", allocationSize=100)
    //@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_seq")
    @Column(name = "USER_ID")
    @JsonIgnore
    long id;

    @Column(name = "UUID", unique = true)
    UUID uuid;

    @Column(name = "FIRSTNAME", nullable = false)
    String firstname;

    @Column(name = "LASTNAME", nullable = false)
    String lastname;

    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "owner", cascade = {CascadeType.REFRESH})
    @OrderBy("dueDate DESC")
    List<Task> ownedTaskList;

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToMany(mappedBy = "collaborators", cascade = {CascadeType.REFRESH})
    List<Task> asCollaboratorTaskList;

    public static User.UserBuilder builder(String firstname, String lastname) {
        return new UserBuilder().uuid(UUID.randomUUID()).firstname(firstname).lastname(lastname);
    }

}
