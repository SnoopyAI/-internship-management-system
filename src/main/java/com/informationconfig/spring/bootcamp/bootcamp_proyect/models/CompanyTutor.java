package com.informationconfig.spring.bootcamp.bootcamp_proyect.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "company_tutors")

public class CompanyTutor extends User {


    @Column(name = "position", length = 100, nullable = true)
    private String position;

    @Column(name = "company", length = 100, nullable = false)
    private String company;

    @OneToMany(mappedBy = "companyTutor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Intern> intern = new ArrayList<>();

    @OneToMany(mappedBy = "companyTutor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PerformanceReport> reports = new ArrayList<>();

    @OneToMany(mappedBy = "companyTutor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Board> boards = new ArrayList<>();
    
    @OneToMany(mappedBy = "createdByTutorId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Task> createdTasks = new ArrayList<>();

    // Constructors

    public CompanyTutor() {}

    public CompanyTutor(String id, String name, String email, String password, String position, String company){
        super(id, name, email, password);
        this.position = position;
        this.company = company;
    }

    // Getters and setters
    
    public String getPosition() {
        return position;
    }
    public void setPosition(String company) {
        this.position = company;
    }
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public List<PerformanceReport> getReports() {
        return reports;
    }
    public void setReports(List<PerformanceReport> reports) {
        this.reports = reports;
    }
    public List<Intern> getIntern() {
        return intern;
    }
    public void setIntern(List<Intern> intern) {
        this.intern = intern;
    }
    public List<Board> getBoards() {
        return boards;
    }
    public void setBoards(List<Board> boards) {
        this.boards = boards;
    }
    public List<Task> getCreatedTasks() {
        return createdTasks;
    }
    public void setCreatedTasks(List<Task> createdTasks) {
        this.createdTasks = createdTasks;
    }

    // Métodos específicos de CompanyTutor

    public void assignTask(Task task) {
        // Logic to assign a task to an intern
    }
    public void createTeam(Board board) {
        // Logic to create a new team
    }
    public void generatePerformanceReport(PerformanceReport report) {
        // Logic to generate a performance report
    }
    public void approveTask(String taskId) {
        // Logic to approve a task
    }
    
}
