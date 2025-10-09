package com.informationconfig.spring.bootcamp.bootcamp_proyect.dto;

public class UniversityDTO {
    private Integer id;
    private String name;

    public UniversityDTO() {}

    public UniversityDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public UniversityDTO(String name) {
        this.name = name;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}