package com.study.task_manager.api.services;

import com.study.task_manager.api.DTOs.TaskDTO;
import com.study.task_manager.api.DTOs.UpdateStatusDTO;
import com.study.task_manager.api.DTOs.SaveTaskDTO;
import com.study.task_manager.api.errors.TaskNotFoundException;
import com.study.task_manager.api.models.TaskModel;
import com.study.task_manager.api.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public TaskDTO createTask(SaveTaskDTO taskDTO) {
        TaskModel taskModel = new TaskModel(taskDTO);
        taskModel = taskRepository.save(taskModel);
        return new TaskDTO(taskModel);
    }

    public List<TaskDTO> getTasks() {
        List<TaskModel> tasks = taskRepository.findAll();
        return tasks.stream().map(TaskDTO::new).collect(Collectors.toList()); // Converte TaskModel para TaskDTO
    }

    public TaskDTO getTaskById(UUID id) {
        TaskModel taskModel = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found for id: " + id));
        return new TaskDTO(taskModel);
    }

    public TaskDTO updateTask(UUID id, SaveTaskDTO updateTaskDTO) {
        Optional<TaskModel> task = taskRepository.findById(id);
        if(task.isEmpty()){
            throw new TaskNotFoundException("Task not found with id: " + id);
        }
        var taskModel = new TaskModel(updateTaskDTO);
        taskModel.setId(id);
        taskRepository.save(taskModel);
        return new TaskDTO(taskModel);
    }

    public void deleteTask(UUID id) {
        Optional<TaskModel> task = taskRepository.findById(id);
        if(task.isEmpty()){
            throw new TaskNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }

    public void updateStatus(UUID id, UpdateStatusDTO statusDTO) {
        Optional<TaskModel> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            throw new TaskNotFoundException("Task not found with id: " + id);
        }
        TaskModel taskModel = task.get();
        taskModel.setStatus(statusDTO.status);
        taskRepository.save(taskModel);
    }
}


