package fr.univtln.bruno.samples.jpa.todolist.entities.listeners;

import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import lombok.extern.java.Log;

@Log
public class MyEntityListener<T> {
    @PostPersist
    @PostUpdate
    @PostLoad
    private void refreshed(Object o) {
        //log.info("Refreshed : "+o.getClass());
    }
}
