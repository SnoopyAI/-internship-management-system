package com.informationconfig.spring.bootcamp.bootcamp_proyect.dto;

public class AcademyTutorDTO {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String department;
    private Integer universityId;


    public AcademyTutorDTO(String name, String email, String password, String department, Integer universityId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.department = department;
        this.universityId = universityId;
    }


    public AcademyTutorDTO(Integer id, String name, String email, String department, Integer universityId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
        this.universityId = universityId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUniversityId() {
        return universityId;
    }

    public void setUniversityId(Integer universityId) {
        this.universityId = universityId;
    }

    
    
}

