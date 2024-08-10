package com.study.task_manager.controllers;

import java.util.List;
import java.util.UUID;
import com.study.task_manager.DTOs.StatusDTO;
import com.study.task_manager.errors.TaskNotFoundException;
import com.study.task_manager.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.study.task_manager.DTOs.TaskDTO;
import com.study.task_manager.models.TaskModel;
import jakarta.validation.Valid;

@RequestMapping ("/tasks")
@RestController
@CrossOrigin(origins = "*")
public class TaskController {

    TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    //CREATE
    @PostMapping
    public ResponseEntity<TaskModel> createTask (@Valid @RequestBody TaskDTO taskDTO) {
        var taskModel = new TaskModel(taskDTO);
        taskService.createTask(taskDTO);
        return ResponseEntity.status(201).body(taskModel);
    }
    //LIST
    @GetMapping
    public ResponseEntity<Object> listTasks () {
        try{
            List<TaskDTO> tasks = taskService.getTasks();
            return ResponseEntity.ok(tasks);
        } catch (TaskNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    //READ
    @GetMapping("/{id}")
    public ResponseEntity<Object> getTask (@PathVariable("id") UUID id) {
        try {
            TaskDTO task = taskService.getTask(id);
            return ResponseEntity.ok(task);
        } catch (TaskNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    //UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTask (@PathVariable("id") UUID id, @RequestBody @Valid TaskDTO taskDTO) {
        try {
            TaskDTO updatedTask = taskService.updateTask(id, taskDTO);
            return ResponseEntity.ok(updatedTask);
        } catch (TaskNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
    //UPDATE STATUS
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateTaskStatus (@PathVariable("id") UUID id, @RequestBody @Valid StatusDTO statusDTO){
        try {
            taskService.updateStatus(id, statusDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Task status updated");
        } catch (TaskNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    //REMOVE
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask (@PathVariable("id") UUID id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.status(HttpStatus.OK).body("Task deleted");
        } catch (TaskNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
