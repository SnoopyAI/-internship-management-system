package com.informationconfig.spring.bootcamp.bootcamp_proyect.models;

public class Task {
    
    private String taskId;
    private String title;
    private String description;
    private String status; // e.g., "Pending", "In Progress", "Completed"
    private String dueDate;
    private String assignedTo; // User ID of the person assigned to the task
    private String[] evidence;

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
    public String getAssignedTo() {
        return assignedTo;
    }
    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }
    public String[] getEvidence() {
        return evidence;
    }
    public void setEvidence(String[] evidence) {
        this.evidence = evidence;
    }
    public void changeStatus(String newStatus) {
        this.status = newStatus;
    }
    public void assignedUser(String userId) {
        this.assignedTo = userId;
    }
    public void upLoadEvidence(String file) {
        // Logic to upload evidence
    }
    
}
