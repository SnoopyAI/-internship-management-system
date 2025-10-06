package com.informationconfig.spring.bootcamp.bootcamp_proyect.dto;

public class AcademyTutorDTO {
    private String id;
    private String name;
    private String email;
    private String password;
    private String academy;
    private String department;


    public AcademyTutorDTO(String id, String name, String email, String password, String academy, String department) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.academy = academy;
        this.department = department;
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


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getAcademy() {
        return academy;
    }


    public void setAcademy(String academy) {
        this.academy = academy;
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

    
    
}

