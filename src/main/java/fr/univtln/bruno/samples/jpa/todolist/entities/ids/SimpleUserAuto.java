package fr.univtln.bruno.samples.jpa.todolist.entities.ids;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.io.Serializable;

@Log
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode(of="id")
@Getter

@AllArgsConstructor
@Builder

@Entity
@Table(name="EX_SIMPLE_USER_AUTO")
public class SimpleUserAuto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    /*
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @GeneratedValue(strategy = GenerationType.TABLE)
    */
    long id;

    @Setter
    String firstname;

    @Setter
    String lastname;
}
