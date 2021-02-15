package fr.univtln.bruno.samples.jpa.todolist.entities.listeners;

import fr.univtln.bruno.samples.jpa.todolist.entities.Task;
import fr.univtln.bruno.samples.jpa.todolist.entities.TaskContent;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.extern.java.Log;

import java.time.LocalDateTime;

@Log
public class TaskEntityListener {
    @PreUpdate
    @PrePersist
    private void changed(Object object) {
        if (object instanceof Task)
            ((Task) object).setUpdateTime(LocalDateTime.now());
        if (object instanceof TaskContent && (((TaskContent) object).getTask())!=null)
            ((TaskContent) object).getTask().setUpdateTime(LocalDateTime.now());
    }
}
