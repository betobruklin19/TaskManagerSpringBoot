package com.study.task_manager.app;

import com.study.task_manager.api.enums.Status;

import java.util.UUID;

public class StatusModel {

    public UUID id;
    public Status status;

    public StatusModel() {
    }

    public StatusModel(UUID id, Status status) {
        this.id = id;
        this.status = status;
    }
}
