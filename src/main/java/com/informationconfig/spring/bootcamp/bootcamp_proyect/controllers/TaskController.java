package com.informationconfig.spring.bootcamp.bootcamp_proyect.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Task;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.services.TaskService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/add")
    public Task add(@Valid @RequestBody Task task) {
        return this.taskService.addTask(task);
    }

    @PostMapping("/createVariable")
    public ArrayList<Task> createVariable(@RequestBody ArrayList<Task> tasks) {
        return this.taskService.getAllTasks(tasks);
    }
    
    @GetMapping("/ReadAll")
    public ArrayList<Task> getAllTasks() {
        return this.taskService.getAllTasks();
    }

      @GetMapping("/{id}")
    public Task getTaskById(@PathVariable String id) {
        return this.taskService.getTaskById(id);
    }

    @PatchMapping("/{id}")
    public Task updateTask(@PathVariable String id, @Valid @RequestBody Task updatedTask) {
        return this.taskService.updateTask(id, updatedTask);
    }
    
    @DeleteMapping("/{id}")
    public boolean deleteTask(@PathVariable String id) {
        return this.taskService.deleteTask(id);
    }
}
