package com.informationconfig.spring.bootcamp.bootcamp_proyect.dto;
import java.util.List;


public class TaskRequestDTO {

    private String id;
    private String title;
    private String description;
    private String status;
    private String dueDate;
    private List<String> internsId;
    private String assignTo;
    private String createdByTutorId;
    private String listId;

    public TaskRequestDTO() {}

    public TaskRequestDTO(
        String id,
        String title, 
        String description, 
        String status, 
        String dueDate,
        String assignTo, 
        String createdByTutorId, 
        String listId,
        List<String> internsId) {
        
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
        this.assignTo = assignTo;
        this.createdByTutorId = createdByTutorId;
        this.listId = listId;
        this.internsId = internsId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<String> getInternsId() {
        return internsId;
    }

    public void setInternsId(List<String> internsId) {
        this.internsId = internsId;
    }

    public String getAssignTo() {
        return assignTo;
    }

    public void setAssignTo(String assignTo) {
        this.assignTo = assignTo;
    }

    public String getCreatedByTutorId() {
        return createdByTutorId;
    }

    public void setCreatedByTutorId(String createdByTutorId) {
        this.createdByTutorId = createdByTutorId;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    
}
