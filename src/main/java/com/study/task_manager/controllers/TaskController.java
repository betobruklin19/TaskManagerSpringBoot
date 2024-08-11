package com.study.task_manager.controllers;

import java.util.List;
import java.util.UUID;

import com.study.task_manager.DTOs.TaskDTO;
import com.study.task_manager.DTOs.UpdateStatusDTO;
import com.study.task_manager.errors.TaskNotFoundException;
import com.study.task_manager.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.study.task_manager.DTOs.SaveTaskDTO;
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
    public ResponseEntity<TaskDTO> createTask (@Valid @RequestBody SaveTaskDTO createTaskDTO) {
        var taskDTO = taskService.createTask(createTaskDTO);
        return ResponseEntity.status(201).body(taskDTO);
    }
    //LIST
    @GetMapping
    public ResponseEntity<List<TaskDTO>> listTasks () {
            var taskDTOs = taskService.getTasks();
            return ResponseEntity.ok(taskDTOs);
    }

    //READ
    @GetMapping("/{id}")
    public ResponseEntity<Object> getTaskById (@PathVariable("id") UUID id) {
        try {
            var taskDTO = taskService.getTaskById(id);
            return ResponseEntity.ok(taskDTO);
        } catch (TaskNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    //UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTask (@PathVariable("id") UUID id, @RequestBody @Valid SaveTaskDTO updateTaskDTO) {
        try {
            var updatedTaskDTO = taskService.updateTask(id, updateTaskDTO);
            return ResponseEntity.ok(updatedTaskDTO);
        } catch (TaskNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
    //UPDATE STATUS
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateTaskStatus (@PathVariable("id") UUID id, @RequestBody @Valid UpdateStatusDTO updateStatusDTO){
        try {
            taskService.updateStatus(id, updateStatusDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Task status updated");
        } catch (TaskNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    //REMOVE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask (@PathVariable("id") UUID id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.status(HttpStatus.OK).body("Task deleted");
        } catch (TaskNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
