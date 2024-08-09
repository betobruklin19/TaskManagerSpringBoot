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

//        if(taskOptional.isEmpty()){
//            return ResponseEntity.status(404).body("Task not found");
//        }
//        var taskModel = new TaskModel(taskDTO);
//        taskModel.setId(id);
//        taskService.updateTask(taskModel);
//        return ResponseEntity.status(200).body(taskModel);


    //UPDATE STATUS
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateTaskStatus (@PathVariable("id") UUID id, @RequestBody @Valid StatusDTO statusDTO){
        String message = taskService.updateStatus(id, statusDTO.status);
        return ResponseEntity.status(200).body(message);
    }
    //REMOVE
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask (@PathVariable("id") UUID id) {
        //Optional<TaskModel> task = (Optional<TaskModel>) taskService.getTask(id);
//        if(task.isEmpty()){
//            return ResponseEntity.status(404).body("Task not found");
//        }
//        taskService.deleteTask(id);
        return ResponseEntity.status(200).body("Task deleted");
    }
}
