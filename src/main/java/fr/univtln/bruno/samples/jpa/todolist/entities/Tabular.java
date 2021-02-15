package fr.univtln.bruno.samples.jpa.todolist.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import fr.univtln.bruno.samples.jpa.todolist.entities.listeners.TaskEntityListener;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.io.Serializable;
import java.util.List;
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

@Entity
@EntityListeners(TaskEntityListener.class)
@NamedQueries({
        @NamedQuery(name = "tabular.findAll", query = "select tabular from Tabular tabular"),
        @NamedQuery(name = "tabular.findbyUUID", query = "select tabular from Tabular tabular where tabular.uuid = :uuid")
})
public class Tabular implements Serializable {
    @Id
    @GeneratedValue
    //@SequenceGenerator(name = "tabular_gen", allocationSize = 100)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tabular_gen")
    @JsonIgnore
    long id;

    @Column(name = "UUID", unique = true)
    UUID uuid = UUID.randomUUID();

    @Column(name = "TITLE", nullable = false)
    String title;

    @ToString.Exclude
    //@JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "tabular", cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    List<Task> tasks;

    @Builder(builderMethodName = "newInstance")
    private Tabular(String title, @Singular List<Task> tasks) {
        this.uuid = UUID.randomUUID();
        this.title = title;
        tasks.forEach(t -> t.setTabular(this));
        this.tasks = tasks;
    }

    public static Tabular.TabularBuilder newInstance(String title) {
        return new TabularBuilder().title(title);
    }
}
