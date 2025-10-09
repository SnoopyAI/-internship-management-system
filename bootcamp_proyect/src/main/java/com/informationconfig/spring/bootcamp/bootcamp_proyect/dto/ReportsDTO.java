package com.informationconfig.spring.bootcamp.bootcamp_proyect.dto;

import java.time.LocalDate;

public class ReportsDTO {
    private Integer id;
    private LocalDate reportDate;
    private String content;
    private Integer academyTutorId;
    private Integer companyTutorId;

    public ReportsDTO(LocalDate reportDate, String content, Integer academyTutorId, Integer companyTutorId) {

        this.reportDate = reportDate;
        this.content = content;
        this.academyTutorId = academyTutorId;
        this.companyTutorId = companyTutorId;
    }

    public ReportsDTO(Integer id, LocalDate reportDate, String content, Integer academyTutorId, Integer companyTutorId) {
        this.id = id;
        this.reportDate = reportDate;
        this.content = content;
        this.academyTutorId = academyTutorId;
        this.companyTutorId = companyTutorId;
    }

    public ReportsDTO() {
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
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
    public Integer getAcademyTutorId() {
        return academyTutorId;
    }
    public void setAcademyTutorId(Integer academyTutorId) {
        this.academyTutorId = academyTutorId;
    }
    public Integer getCompanyTutorId() {
        return companyTutorId;
    }
    public void setCompanyTutorId(Integer companyTutorId) {
        this.companyTutorId = companyTutorId;
    }

    
}
