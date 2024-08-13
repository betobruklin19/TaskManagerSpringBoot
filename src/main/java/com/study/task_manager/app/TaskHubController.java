package com.study.task_manager.app;

import com.study.task_manager.api.DTOs.TaskDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@Controller
public class TaskHubController {

    @GetMapping("/task-hub")
    public ModelAndView taskHub() {
        ModelAndView modelAndView = new ModelAndView("task-hub");
        RestTemplate restTemplate = new RestTemplate();
        String apiURL = "http://localhost:8080";
        List<TaskDTO> responseData = restTemplate.exchange(
                apiURL + "/api/tasks",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TaskDTO>>() {
        }).getBody();
        System.out.println(responseData);
        modelAndView.addObject("tasks", responseData);
        return modelAndView;
    }

    @PostMapping("/remove/{id}")
    public String removeTask(@PathVariable UUID id) {
        RestTemplate restTemplate = new RestTemplate();
        String apiURL = "http://localhost:8080";
        restTemplate.delete(apiURL + "/api/tasks/" + id);
        return "redirect:/task-hub";
    }
}
