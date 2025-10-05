package com.informationconfig.spring.bootcamp.bootcamp_proyect.dto;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Task;

public class TaskRequestDTO {
    private String tutorId;
    private Task task;

    public TaskRequestDTO() {}

    public String getTutorId() {
        return tutorId;
    }

    public void setTutorId(String tutorId) {
        this.tutorId = tutorId;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
