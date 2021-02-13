package fr.univtln.bruno.samples.jpa.todolist.entities.ids.embedded;

import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.io.Serializable;

@Log
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor

@Embeddable
public class EmbeddedUserID implements Serializable {
    long groupId;
    long userId;
}
