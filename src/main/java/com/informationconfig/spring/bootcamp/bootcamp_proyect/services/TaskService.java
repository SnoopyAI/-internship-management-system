package com.informationconfig.spring.bootcamp.bootcamp_proyect.services;
import org.springframework.stereotype.Service;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Task;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.TaskRepository;
import java.util.ArrayList;

@Service
public class TaskService {
    
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task addTask(Task task, String tutorId) {
        task.setCreatedByTutorId(tutorId);
        return taskRepository.addTask(task);
    }

    public ArrayList<Task> getAllTasks() {
        return taskRepository.getAllTasks();
    }

    public boolean deleteTask(String id) {
        return taskRepository.deleteTask(id);
    }

    public Task updateTask(String id, Task updatedTask) {
        return taskRepository.updateTask(id, updatedTask);
    }

    public Task getTaskById(String id) {
        return taskRepository.getTaskById(id);
    }

    public ArrayList<Task> getAllTasks(ArrayList<Task> tasks) {
        return taskRepository.getAllTasks(tasks);
    }
}
