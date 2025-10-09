package com.informationconfig.spring.bootcamp.bootcamp_proyect.dto;
import java.util.List;


public class TaskRequestDTO {

    private Integer id;
    private String title;
    private String description;
    private String status;
    private String dueDate;
    private List<Integer> internsId;
    private Integer assignTo;
    private Integer createdByTutorId;
    private Integer listId;

    public TaskRequestDTO() {}

    public TaskRequestDTO(
        String title, 
        String description, 
        String status, 
        String dueDate,
        Integer assignTo, 
        Integer createdByTutorId, 
        Integer listId,
        List<Integer> internsId) {

        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
        this.assignTo = assignTo;
        this.createdByTutorId = createdByTutorId;
        this.listId = listId;
        this.internsId = internsId;
    }

    public TaskRequestDTO(
        Integer id,
        String title, 
        String description, 
        String status, 
        String dueDate,
        Integer assignTo, 
        Integer createdByTutorId, 
        Integer listId,
        List<Integer> internsId) {

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public List<Integer> getInternsId() {
        return internsId;
    }

    public void setInternsId(List<Integer> internsId) {
        this.internsId = internsId;
    }

    public Integer getAssignTo() {
        return assignTo;
    }

    public void setAssignTo(Integer assignTo) {
        this.assignTo = assignTo;
    }

    public Integer getCreatedByTutorId() {
        return createdByTutorId;
    }

    public void setCreatedByTutorId(Integer createdByTutorId) {
        this.createdByTutorId = createdByTutorId;
    }

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

    
}
