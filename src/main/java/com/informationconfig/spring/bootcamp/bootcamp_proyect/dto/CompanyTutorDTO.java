package com.informationconfig.spring.bootcamp.bootcamp_proyect.dto;

public class CompanyTutorDTO {
    
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String position;
    private Integer companyId;

    public CompanyTutorDTO(String name, String email, String password, String position, Integer companyId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.position = position;
        this.companyId = companyId;
    }

    public CompanyTutorDTO(Integer id, String name, String email, String position, Integer companyId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.position = position;
        this.companyId = companyId;
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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    
}
