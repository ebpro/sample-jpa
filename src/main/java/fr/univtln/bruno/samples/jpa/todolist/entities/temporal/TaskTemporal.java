package fr.univtln.bruno.samples.jpa.todolist.entities.temporal;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@Table(name = "EX_TASK_TEMPORAL")
public class TaskTemporal {
    @Id
    @GeneratedValue
    long id;

    String title;

    String description;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "DUE_DATE")
    Date dueDate;
    //Equivalent to java.sql.Date dueDate;

    @Temporal(value = TemporalType.TIME)
    @Column(name = "CREATION_TIME")
    Date creationTime;
    //Equivalent to java.sql.Time creationTime;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "UPDATE_TIME")
    Date updateTime;
    //Equivalent to java.sql.Timestamp updateTime


}
