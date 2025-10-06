package com.informationconfig.spring.bootcamp.bootcamp_proyect.controllers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.informationconfig.spring.bootcamp.bootcamp_proyect.dto.TaskRequestDTO;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Task;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.services.TaskService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public TaskRequestDTO add(@RequestBody TaskRequestDTO dto) {
    Task newTask = taskService.addTask(dto, dto.getCreatedByTutorId());
    // Evitar recursividad: solo devolver IDs de internos
    List<String> internIds = newTask.getInterns() != null ? newTask.getInterns().stream().map(i -> i.getId()).toList()
            : List.of();
        return new TaskRequestDTO(
            newTask.getTaskId(),
            newTask.getTitle(),
            newTask.getDescription(),
            newTask.getDueDate(),
            newTask.getStatus(),
            newTask.getAssignTo(),
            newTask.getCreatedByTutorId(),
            newTask.getList() != null ? newTask.getList().getListId() : null,
            internIds

        );
}

    @PostMapping("/createVariable")
    public List<Task> createVariable(@RequestBody List<Task> tasks) {
        return this.taskService.getAllTasks();
    }
    
    @GetMapping("/ReadAll")
public List<TaskRequestDTO> getAllTasks() {
    List<Task> tasks = this.taskService.getAllTasks();
    return tasks.stream().map(t -> new TaskRequestDTO(
        t.getTaskId(),
        t.getTitle(),
        t.getDescription(),
        t.getDueDate(),
        t.getStatus(),
        t.getAssignTo(),
        t.getCreatedByTutorId(),
        t.getList() != null ? t.getList().getListId() : null,
        t.getInterns() != null
            ? t.getInterns().stream().map(i -> i.getId()).toList()
            : List.of()
    )).toList();
}

@GetMapping("/{id}")
public Optional<TaskRequestDTO> getTaskById(@PathVariable String id) {
    return this.taskService.getTaskById(id).map(t -> new TaskRequestDTO(
        t.getTaskId(),
        t.getTitle(),
        t.getDescription(),
        t.getDueDate(),
        t.getStatus(),
        t.getAssignTo(),
        t.getCreatedByTutorId(),
        t.getList() != null ? t.getList().getListId() : null,
        t.getInterns() != null
            ? t.getInterns().stream().map(i -> i.getId()).toList()
            : List.of()
    ));
}


    @PatchMapping("/{id}")
    public TaskRequestDTO updateTask(@PathVariable String id, @Valid @RequestBody TaskRequestDTO dto) {
        // Solo actualizar si existe
        Task existing = this.taskService.getTaskById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        
        Task updated = this.taskService.updateTask(id, dto);
        return new TaskRequestDTO(
            updated.getTaskId(),
            updated.getTitle(),
            updated.getDescription(),
            updated.getStatus(),
            updated.getDueDate(),
            updated.getAssignTo(),
            updated.getCreatedByTutorId(),
            updated.getList() != null ? updated.getList().getListId() : null,
            updated.getInterns() != null 
                ? updated.getInterns().stream().map(intern -> intern.getId()).collect(Collectors.toList())
                : List.of()
        );
    }
    
    @DeleteMapping("/{id}")
    public boolean deleteTask(@PathVariable String id) {
        return this.taskService.deleteTask(id);
    }
}
