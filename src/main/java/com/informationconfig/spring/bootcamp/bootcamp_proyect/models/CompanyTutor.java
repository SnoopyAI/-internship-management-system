package com.informationconfig.spring.bootcamp.bootcamp_proyect.models;


public class CompanyTutor extends User {

    public String position;
    public String department;
    public String reports;
    
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public String getReports() {
        return reports;
    }
    public void setReports(String reports) {
        this.reports = reports;
    }
    public void assignTask(Task task) {
        // Logic to assign a task to an intern
    }
    public void createTeam(Team team) {
        // Logic to create a new team
    }
    public void generatePerformanceReport(PerformanceReport report) {
        // Logic to generate a performance report
    }
    public void approveTask(String taskId) {
        // Logic to approve a task
    }
}
