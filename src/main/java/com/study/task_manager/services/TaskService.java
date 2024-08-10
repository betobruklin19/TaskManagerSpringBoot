package com.study.task_manager.services;

import com.study.task_manager.DTOs.StatusDTO;
import com.study.task_manager.DTOs.TaskDTO;
import com.study.task_manager.errors.TaskNotFoundException;
import com.study.task_manager.models.TaskModel;
import com.study.task_manager.repositories.TaskRepository;
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

    public TaskModel createTask(TaskDTO taskDTO) {
        TaskModel taskModel = new TaskModel(taskDTO);
        taskModel = taskRepository.save(taskModel);
        return taskModel;
    }

    public List<TaskDTO> getTasks() {
        List<TaskModel> tasks = taskRepository.findAll();
        if (tasks.isEmpty()) {
            throw new TaskNotFoundException("Tasks not found");
        }
        return tasks.stream()
                .map(TaskDTO::new)  // Converte TaskModel para TaskDTO
                .collect(Collectors.toList());
    }

    public TaskDTO getTask(UUID id) {
        TaskModel taskModel = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found for id: " + id));
        return new TaskDTO(taskModel);
    }

    public TaskDTO updateTask(UUID id, TaskDTO taskDTO) {
        TaskModel taskModel = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found for id: " + id));
        // Atualiza os dados da tarefa
        taskModel = new TaskModel(taskDTO);
        taskModel.setId(id);
        // Salva a tarefa atualizada
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

    public void updateStatus(UUID id, StatusDTO statusDTO) {
        Optional<TaskModel> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            throw new TaskNotFoundException("Task not found with id: " + id);
        }
        TaskModel taskModel = task.get();
        taskModel.setStatus(statusDTO.status);
        taskRepository.save(taskModel);
    }
}


