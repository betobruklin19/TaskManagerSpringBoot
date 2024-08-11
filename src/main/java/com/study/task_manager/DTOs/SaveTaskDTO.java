package com.study.task_manager.DTOs;

import com.study.task_manager.enums.Status;
import com.study.task_manager.models.TaskModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public class SaveTaskDTO {

    private UUID id;
    @NotBlank
    public String name;

    public UUID getId() {
        return id;
    }

    @NotBlank
    public String description;

    public Status status;

    @NotNull
    @Future
    public LocalDateTime dueDate;

}
