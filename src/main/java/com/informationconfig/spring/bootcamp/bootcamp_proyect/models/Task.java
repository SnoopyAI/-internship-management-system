package com.informationconfig.spring.bootcamp.bootcamp_proyect.models;

public class Task {
    
    private String taskId;
    private String title;
    private String description;
    private String status; // e.g., "To Do", "In Progress", "Done"
    private String dueDate; // e.g., "2023-12-31"
    private String[] evidences;
    private String assingTo;


    public String getTaskId() {
        return taskId;
    }
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getDueDate() {
        return dueDate;
    }
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
    public String[] getEvidences() {
        return evidences;
    }
    public void setEvidences(String[] evidences) {
        this.evidences = evidences;
    }
    public String getAssingTo() {
        return assingTo;
    }
    public void setAssingTo(String assingTo) {
        this.assingTo = assingTo;
    }

    public void changeStatus(String newStatus) {
        this.status = newStatus;
    }

    public void uploadEvidence(String[] evidence) {
        // Logic to upload and attach evidence to the task
    }   
    public void assignTask(String userId) {
        this.assingTo = userId;
    }
}
