package com.informationconfig.spring.bootcamp.bootcamp_proyect.dto;

public class ListDTO {
    private String id;
    private String name;
    private String boardId;
    

    //  Constructor vacío
    public ListDTO() {}

    //  Constructor completo
    public ListDTO(String id, String name, String boardId) {
        this.id = id;
        this.name = name;
        this.boardId = boardId;

        
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
    public String getBoardId() {
        return boardId;
    }
    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

}
