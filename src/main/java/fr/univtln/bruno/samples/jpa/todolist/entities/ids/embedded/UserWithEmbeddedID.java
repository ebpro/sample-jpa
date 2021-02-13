package fr.univtln.bruno.samples.jpa.todolist.entities.ids.embedded;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

@Log
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "EX_USER_WITH_EMBEDDED_ID")
public class UserWithEmbeddedID {
    @EmbeddedId
    EmbeddedUserID embeddedUserID;

    String firstname;

    String lastname;
}

