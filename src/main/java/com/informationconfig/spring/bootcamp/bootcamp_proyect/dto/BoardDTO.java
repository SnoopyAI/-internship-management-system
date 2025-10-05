package com.informationconfig.spring.bootcamp.bootcamp_proyect.dto;
import java.time.LocalDate;

public class BoardDTO {
    private String id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String academyTutorId;
    private String companyTutorId;
 
    
    

    public BoardDTO(String id, String name, String description, LocalDate startDate, LocalDate endDate,
            String academyTutorId, String companyTutorId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.academyTutorId = academyTutorId;
        this.companyTutorId = companyTutorId;
      
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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
