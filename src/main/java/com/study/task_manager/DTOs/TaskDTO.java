package com.study.task_manager.DTOs;

import com.study.task_manager.enums.Status;
import com.study.task_manager.models.TaskModel;

import java.time.LocalDateTime;
import java.util.UUID;

public class TaskDTO {

    private UUID id;

    public String name;

    public UUID getId() {
        return id;
    }

    public String description;

    public Status status;

    public LocalDateTime dueDate;

    public TaskDTO() {
    }

    public TaskDTO(TaskModel taskModel) {
        this.id = taskModel.getId();
        this.name = taskModel.getName();
        this.description = taskModel.getDescription();
        this.dueDate = taskModel.getDueDate();
        this.status = taskModel.getStatus();
    }
}
