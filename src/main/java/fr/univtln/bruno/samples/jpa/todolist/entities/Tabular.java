package fr.univtln.bruno.samples.jpa.todolist.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import fr.univtln.bruno.samples.jpa.todolist.entities.listeners.MyEntityListener;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Log
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Builder

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "uuid")

@Entity
@EntityListeners(MyEntityListener.class)
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
    @Builder.Default
    UUID uuid = UUID.randomUUID();

    @Column(name = "TITLE", nullable = false)
    String title;

    //@JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "tabular", cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @Singular("Task")
    List<Task> taskList;
}
