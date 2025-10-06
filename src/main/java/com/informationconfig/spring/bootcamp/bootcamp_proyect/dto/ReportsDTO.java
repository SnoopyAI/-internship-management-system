package com.informationconfig.spring.bootcamp.bootcamp_proyect.dto;

import java.time.LocalDate;

public class ReportsDTO {
    private String id;
    private LocalDate reportDate;
    private String content;
    private String academyTutorId;
    private String companyTutorId;

    
    public ReportsDTO(String id, LocalDate reportDate, String content, String academyTutorId, String companyTutorId) {
        this.id = id;
        this.reportDate = reportDate;
        this.content = content;
        this.academyTutorId = academyTutorId;
        this.companyTutorId = companyTutorId;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public LocalDate getReportDate() {
        return reportDate;
    }
    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getAcademyTutorId() {
        return academyTutorId;
    }
    public void setAcademyTutorId(String academyTutorId) {
        this.academyTutorId = academyTutorId;
    }
    public String getCompanyTutorId() {
        return companyTutorId;
    }
    public void setCompanyTutorId(String companyTutorId) {
        this.companyTutorId = companyTutorId;
    }

    
}
