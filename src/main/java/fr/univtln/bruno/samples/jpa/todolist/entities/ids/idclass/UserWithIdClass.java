package fr.univtln.bruno.samples.jpa.todolist.entities.ids.idclass;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
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
@IdClass(UserID.class)
@Table(name = "EX_USER_WITH_IDCLASS")
public class UserWithIdClass {
    @Id
    long groupId;

    @Id
    long userId;

    String firstname;

    String lastname;
}

