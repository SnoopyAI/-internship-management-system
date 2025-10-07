package com.informationconfig.spring.bootcamp.bootcamp_proyect.dto;

public class CompanyTutorDTO {
    
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String position;
    private String company;

    public CompanyTutorDTO(String name, String email, String password, String position, String company) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.position = position;
        this.company = company;
    }

    public CompanyTutorDTO(Integer id, String name, String email, String position, String company) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.position = position;
        this.company = company;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    
}
