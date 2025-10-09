package com.informationconfig.spring.bootcamp.bootcamp_proyect.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lists")
public class Lists {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "list_id", length = 50, nullable = false, unique = true)
    private Integer listId;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @OneToMany(mappedBy = "list", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Task> tasks = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false, foreignKey = @ForeignKey(name = "FK_BOARD_LIST"))
    private Board board;

    // Constructors

    public Lists() {}

    public Lists(Integer id, String name) {
        listId = id;
        this.name = name;
    } 

    // Getters and Setters

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
