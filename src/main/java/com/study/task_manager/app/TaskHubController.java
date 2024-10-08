package com.study.task_manager.app;

import com.study.task_manager.api.DTOs.TaskDTO;
import com.study.task_manager.api.DTOs.UpdateStatusDTO;
import com.study.task_manager.api.configs.ApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class TaskHubController {

    @Autowired
    private ApiConfig apiConfig;

    @GetMapping("/task-hub")
    public ModelAndView taskHub() {
        ModelAndView modelAndView = new ModelAndView("task-hub");
        RestTemplate restTemplate = new RestTemplate();
        String url = apiConfig.getBaseUrl() + "/api/tasks";
        ResponseEntity<List<TaskDTO>> responseEntity = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<TaskDTO>>() {}
        );
        List<TaskDTO> responseData = responseEntity.getBody();
        modelAndView.addObject("tasks", responseData);
        return modelAndView;
    }

    @GetMapping("/new-task")
    public ModelAndView newTask() {
        ModelAndView modelAndView = new ModelAndView("new-task");
        TaskModel taskModel = new TaskModel();
        modelAndView.addObject("task", taskModel);
        return modelAndView;
    }

    @PostMapping("/new-task")
    public String createTask(TaskModel taskModel) {
        RestTemplate restTemplate = new RestTemplate();
        String url = apiConfig.getBaseUrl() + "/api/tasks";
        Map<String, Object> body = new HashMap<>();
        body.put("name", taskModel.getName());
        body.put("description", taskModel.getDescription());
        body.put("dueDate", taskModel.getDueDate());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        restTemplate.postForEntity(url, request, String.class);
        return "redirect:/task-hub";
    }

    @PostMapping("/update-status")
    public String updateStatus(@RequestBody StatusModel statusModel) {
        RestTemplate restTemplate = new RestTemplate();
        String url = apiConfig.getBaseUrl() + "/api/tasks/" + statusModel.id + "/status";
        Map<String, Object> body = new HashMap<>();
        body.put("status", statusModel.status);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        restTemplate.put(url, request, String.class);
        return "redirect:/task-hub";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") UUID id) {
        ModelAndView modelAndView = new ModelAndView("edit-task");
        RestTemplate restTemplate = new RestTemplate();
        String url = apiConfig.getBaseUrl() + "/api/tasks/" + id;
        ResponseEntity<TaskDTO> responseEntity = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<TaskDTO>() {}
        );
        TaskDTO responseData = responseEntity.getBody();
        var taskModel = new TaskModel(responseData.name, responseData.description, responseData.dueDate.toString());
        taskModel.setId(responseData.getId());
        taskModel.setStatus(responseData.status);
        modelAndView.addObject("task", taskModel);
        return modelAndView;
    }

    @PostMapping("/edit-task")
    public String updateTask(TaskModel taskModel) {
        RestTemplate restTemplate = new RestTemplate();
        String url = apiConfig.getBaseUrl() + "/api/tasks/" + taskModel.getId();
        Map<String, Object> body = new HashMap<>();
        body.put("name", taskModel.getName());
        body.put("description", taskModel.getDescription());
        body.put("dueDate", taskModel.getDueDate());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        restTemplate.put(url, request, String.class);
        return "redirect:/task-hub";
    }


    @PostMapping("/remove/{id}")
    public String removeTask(@PathVariable UUID id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = apiConfig.getBaseUrl() + "/api/tasks/" + id;
        restTemplate.delete(url);
        return "redirect:/task-hub";
    }
}
