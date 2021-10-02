package com.himsoomzzin.calender.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "todolist")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToDoListEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String author;  // userId

    private String startDate;

    private String endDate;

    private String content;

    @OneToMany(mappedBy = "toDoListEntity")
    private List<ToDoListTeamEntity> toDoListTeamEntities = new ArrayList<>();

    @OneToMany(mappedBy = "toDoListEntity")
    private List<TaskEntity> taskEntities = new ArrayList<>();
}