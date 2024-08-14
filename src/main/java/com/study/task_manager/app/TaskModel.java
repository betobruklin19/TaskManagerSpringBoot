package com.study.task_manager.app;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class TaskModel {

    private String name;

    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dueDate;

    public TaskModel(String name, String description, LocalDateTime dueDate) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
    }

    public TaskModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
}

