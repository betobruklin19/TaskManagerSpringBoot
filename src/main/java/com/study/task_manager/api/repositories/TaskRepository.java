package com.study.task_manager.api.repositories;

import com.study.task_manager.api.models.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, UUID> {

}
