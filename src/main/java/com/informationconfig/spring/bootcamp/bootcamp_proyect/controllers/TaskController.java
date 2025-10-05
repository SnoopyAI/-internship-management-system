package com.informationconfig.spring.bootcamp.bootcamp_proyect.controllers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.informationconfig.spring.bootcamp.bootcamp_proyect.dto.TaskRequestDTO;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Task;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.services.TaskService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/add")
    public Task add(@RequestBody TaskRequestDTO request) {
        // Solo crear, no actualizar
        request.getTask().setTaskId(null); // Asegura que no se actualice una existente
        return this.taskService.addTask(request.getTask(), request.getTutorId());
    }

    @PostMapping("/createVariable")
    public List<Task> createVariable(@RequestBody List<Task> tasks) {
        return this.taskService.getAllTasks();
    }
    
    @GetMapping("/ReadAll")
    public List<Task> getAllTasks() {
        return this.taskService.getAllTasks();
    }

      @GetMapping("/{id}")
    public Optional<Task> getTaskById(@PathVariable String id) {
        return this.taskService.getTaskById(id);
    }

    @PatchMapping("/{id}")
    public Task updateTask(@PathVariable String id, @Valid @RequestBody Task updatedTask) {
        // Solo actualizar si existe
        Task existing = this.taskService.getTaskById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        updatedTask.setTaskId(id); // Mantener el mismo ID
        return this.taskService.updateTask(id, updatedTask);
    }
    
    @DeleteMapping("/{id}")
    public boolean deleteTask(@PathVariable String id) {
        return this.taskService.deleteTask(id);
    }
}
