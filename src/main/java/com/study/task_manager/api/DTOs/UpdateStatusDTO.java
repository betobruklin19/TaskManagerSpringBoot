package com.study.task_manager.api.DTOs;

import com.study.task_manager.api.enums.Status;
import jakarta.validation.constraints.NotNull;

public class UpdateStatusDTO {

    @NotNull
    public Status status;

}
