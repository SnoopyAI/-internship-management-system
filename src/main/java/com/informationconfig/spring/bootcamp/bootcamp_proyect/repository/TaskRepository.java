package com.informationconfig.spring.bootcamp.bootcamp_proyect.repository;
import org.springframework.stereotype.Repository;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Task;
import java.util.ArrayList;

@Repository
public class TaskRepository {
    
    private ArrayList<Task> tasks = new ArrayList<>();

    public Task addTask(Task task) {
        tasks.add(task);
        return task;
    }

    public ArrayList<Task> getAllTasks(ArrayList<Task> tasks) {
        tasks.addAll(tasks);
        return tasks;
    }

    public boolean deleteTask(String id) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getTaskId() == id) {
                tasks.remove(i);
                return true;
            }
        }
        return false;
    }

    public Task updateTask(String id, Task updatedTask) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getTaskId().equals(id)) {
                tasks.set(i, updatedTask);
                return updatedTask;
            }
        }
        return null; // or throw an exception if preferred
    }

    public Task getTaskById(String id) {
        for (Task task : tasks) {
            if (task.getTaskId().equals(id)) {
                return task;
            }
        }
        return null; // or throw an exception if preferred
    }

    public ArrayList<Task> getAllTasks() {
        return tasks;
    }
}
