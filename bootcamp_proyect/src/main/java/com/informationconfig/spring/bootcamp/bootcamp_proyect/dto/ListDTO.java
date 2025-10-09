package com.informationconfig.spring.bootcamp.bootcamp_proyect.dto;

public class ListDTO {
    private Integer id;
    private String name;
    private Integer boardId;

    //  Constructor vacío
    public ListDTO() {}

    //  Constructor completo
    public ListDTO(String name, Integer boardId) {
        this.name = name;
        this.boardId = boardId;

    }

    public ListDTO(Integer id, String name, Integer boardId) {
        this.id = id;
        this.name = name;
        this.boardId = boardId;
    }

    // Getters y Setters
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
    public Integer getBoardId() {
        return boardId;
    }
    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

}
