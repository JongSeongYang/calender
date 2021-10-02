package com.himsoomzzin.calender.domain.entity;

import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "task")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String userId;

    @Nullable
    private String content;

    @ColumnDefault("false")
    private boolean isComplete;

    @ManyToOne
    @JoinColumn(name = "todolist_id")
    private ToDoListEntity toDoListEntity;
}
