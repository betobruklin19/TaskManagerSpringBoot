package com.study.task_manager.DTOs;

import com.study.task_manager.enums.Status;
import jakarta.validation.constraints.NotNull;

public class StatusDTO {

    @NotNull
    public Status status;

}
