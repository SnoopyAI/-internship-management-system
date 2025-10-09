package com.informationconfig.spring.bootcamp.bootcamp_proyect.dto;
import java.time.LocalDate;

public class BoardDTO {
    private Integer id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer academyTutorId;
    

    public BoardDTO(String name, String description, LocalDate startDate, LocalDate endDate,
            Integer academyTutorId) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.academyTutorId = academyTutorId;
        
      
    }
    
    public BoardDTO(Integer id, String name, String description, LocalDate startDate, LocalDate endDate,
            Integer academyTutorId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.academyTutorId = academyTutorId;
    }

    public BoardDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getAcademyTutorId() {
        return academyTutorId;
    }

    public void setAcademyTutorId(Integer academyTutorId) {
        this.academyTutorId = academyTutorId;
    }

}
