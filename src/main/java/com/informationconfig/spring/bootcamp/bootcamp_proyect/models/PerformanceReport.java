package com.informationconfig.spring.bootcamp.bootcamp_proyect.models;

public class PerformanceReport {
    
    private String reportId;
    private String period;
    private String comments;
    private int grade;

    public String getReportId() {
        return reportId;
    }
    public void setReportId(String reportId) {
        this.reportId = reportId;
    }
    public String getPeriod() {
        return period;
    }
    public void setPeriod(String period) {
        this.period = period;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
    public int getGrade() {
        return grade;
    }
    public void setGrade(int grade) {
        this.grade = grade;
    }

    public void generateReport() {
        // Logic to generate performance report
    }
    public void submitReport() {
        // Logic to submit the report
    }
    public void viewReport() {
        // Logic to review the report
    }

    
}
