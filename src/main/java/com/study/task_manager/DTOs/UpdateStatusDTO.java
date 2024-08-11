package com.study.task_manager.DTOs;

import com.study.task_manager.enums.Status;
import jakarta.validation.constraints.NotNull;

public class UpdateStatusDTO {

    @NotNull
    public Status status;

}
