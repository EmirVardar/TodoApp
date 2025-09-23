package com.example.todoapp.entities;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "todos")
@Data
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable=false)
    private boolean isDone = false;
}
