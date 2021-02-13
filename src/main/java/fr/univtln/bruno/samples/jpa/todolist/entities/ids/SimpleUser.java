package fr.univtln.bruno.samples.jpa.todolist.entities.ids;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.io.Serializable;

@Log
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode(of = "id")
@Getter

@AllArgsConstructor(staticName = "of")

@Entity
@Table(
        name="EX_SIMPLE_USER",
        uniqueConstraints={
                @UniqueConstraint(name="firstname_lastname", columnNames={"lastname", "firstname"})
        }
)
public class SimpleUser implements Serializable {
    @Id
    long id;

    @Setter
    String firstname;

    @Setter
    String lastname;
}
