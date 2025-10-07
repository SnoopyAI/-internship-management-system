package com.informationconfig.spring.bootcamp.bootcamp_proyect.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Tasks")
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id", length = 50, nullable = false)
    private Integer taskId;

    @Column(name = "Title", length = 100, nullable = false)
    private String title;

    @Column(name = "Description", length = 255, nullable = true)
    private String description;

    @Column(name = "Status", length = 50, nullable = false)
    private String status; // e.g., "To Do", "In Progress", "Done"

    @Column(name = "Due_Date", length = 50, nullable = true)
    private String dueDate; // e.g., "2023-12-31"

    @Column(name = "Assing_To", length = 50, nullable = true)
    private Integer assingTo;

    @Column(name = "created_by_tutor_id", length = 50, nullable = false)
    private Integer createdByTutorId;

    @ManyToOne
    @JoinColumn(name = "list_id", nullable = false, foreignKey = @ForeignKey(name = "FK_LIST_TASK"))
    private Lists list;

    @ManyToMany
    @JoinTable(
        name = "task_interns",
        joinColumns = @JoinColumn(name = "task_id"),
        inverseJoinColumns = @JoinColumn(name = "intern_id")
    )
    private List<Intern> interns = new ArrayList<>();

    // Constructors

    public Task(){}

    public Task(Integer id, String title, String description, String status, String dueDate, Integer assingTo, Integer createdByTutorId){
        taskId = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
        this.assingTo = assingTo;
        this.createdByTutorId = createdByTutorId;
    }

    // Getters and Setters

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
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

    public Integer getAssignTo() {
        return assingTo;
    }

    public void setAssingTo(Integer assingTo) {
        this.assingTo = assingTo;
    }

    public Integer getCreatedByTutorId() {
        return createdByTutorId;
    }

    public void setCreatedByTutorId(Integer createdByTutorId) {
        this.createdByTutorId = createdByTutorId;
    }

    public Lists getList() {
        return list;
    }

    public void setList(Lists list) {
        this.list = list;
    }

    public List<Intern> getInterns() {
        return interns;
    }

    public void setInterns(List<Intern> interns) {
        this.interns = interns;
    }

    // Metodos específicos de Task

    public void changeStatus(String newStatus) {
        this.status = newStatus;
    }

    public void uploadEvidence(String[] evidence) {
        // Logic to upload and attach evidence to the task
    }   
    public void assignTask(Integer userId) {
        this.assingTo = userId;
    }
    
}