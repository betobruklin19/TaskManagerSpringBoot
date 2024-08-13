package com.study.task_manager.api.models;

import com.study.task_manager.api.DTOs.SaveTaskDTO;
import com.study.task_manager.api.enums.Status;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tasks")
public class TaskModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 255)
    private String description;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'TODO'") // Define o valor padrão diretamente no banco de dados
    @Column(nullable = false)
    private Status status = Status.TODO;

    @Column(name = "due_date",nullable = false)
    private LocalDateTime dueDate;

    public TaskModel() {
    }

    public TaskModel(SaveTaskDTO taskDTO) {
        this.name = taskDTO.name;
        this.description = taskDTO.description;
        this.dueDate = taskDTO.dueDate;
        if(taskDTO.status != null) {
            setStatus(taskDTO.status);
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }


    public LocalDateTime getDueDate() {
        return dueDate;
    }




}
