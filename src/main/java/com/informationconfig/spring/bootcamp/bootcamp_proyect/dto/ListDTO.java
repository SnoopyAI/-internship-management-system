package com.informationconfig.spring.bootcamp.bootcamp_proyect.dto;

public class ListDTO {
    private String id;
    private String name;
    private String description;
    private Integer priority; // <- aquí decides el tipo según el modelo original

    //  Constructor vacío
    public ListDTO() {}

    //  Constructor completo
    public ListDTO(String id, String name, String description, Integer priority) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.priority = priority;
    }

    // Getters y Setters
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
    public Integer getPriority() {
        return priority;
    }
    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
