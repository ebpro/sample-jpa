package fr.univtln.bruno.samples.jpa.todolist.entities.ids.idclass;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.io.Serializable;

@Log
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class UserID implements Serializable {
    long groupId;
    long userId;
}
