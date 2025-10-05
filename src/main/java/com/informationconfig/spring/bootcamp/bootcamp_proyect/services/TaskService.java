package com.informationconfig.spring.bootcamp.bootcamp_proyect.services;
import org.springframework.stereotype.Service;

import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Task;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.TaskRepository;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Crear tarea
    public Task addTask(Task task, String tutorId) {
        task.setCreatedByTutorId(tutorId);
        return taskRepository.save(task);
    }

    // Obtener todas las tareas
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Obtener una tarea por ID
    public Optional<Task> getTaskById(String id) {
        return taskRepository.findById(id);
    }

    // Actualizar una tarea
    public Task updateTask(String id, Task updatedTask) {
        if (taskRepository.existsById(id)) {
            updatedTask.setTaskId(id);
            return taskRepository.save(updatedTask);
        }
        return null;
    }

    // Eliminar una tarea
    public boolean deleteTask(String id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

   
