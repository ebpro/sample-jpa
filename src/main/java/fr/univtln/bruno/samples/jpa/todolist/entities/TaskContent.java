package fr.univtln.bruno.samples.jpa.todolist.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.io.Serializable;

@Log
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = "description")
@EqualsAndHashCode(of ="description")
@Getter

@Entity
public class TaskContent implements Serializable {
    @JsonIgnore
    @Id
    @GeneratedValue
    //@SequenceGenerator(name="task_gen", allocationSize=100)
    //@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="task_gen")
    long id;

    @JsonIgnore
    @OneToOne(mappedBy = "taskContent")
    Task task;

    @Setter
    String description;

    @Builder
    public TaskContent(String description) {
        this.description = description;
    }
}
