package com.informationconfig.spring.bootcamp.bootcamp_proyect.models;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false, length = 50)
    private Integer id;

    @Column(name = "Name", nullable = false, length = 50)
    private String name;

    @Column(name = "Email", unique = true, nullable = false, length = 80)
    private String email;

    @Column(name = "Password", nullable = false, length = 24)
    private String password;

    //  constructores, getters, setters

    public User(Integer id, String name, String email, String password) {
        this.id = id;
        this.name =  name;
        this.email = email;
        this.password = password;
    }

    public User() {}

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

}