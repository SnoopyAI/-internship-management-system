package com.informationconfig.spring.bootcamp.bootcamp_proyect.dto;

public class CompaniesDTO {
    private Integer id;
    private String name;
    private String address;
    private String nit;
    private String contactEmail;
    private String contactPhone;

    public CompaniesDTO() {}

    public CompaniesDTO(Integer id, String name, String address, String nit, String contactEmail, String contactPhone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.nit = nit;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
    }

    public CompaniesDTO(String name, String address, String nit,  String contactEmail, String contactPhone) {
        this.name = name;
        this.address = address;
        this.nit = nit;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }
    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }
}