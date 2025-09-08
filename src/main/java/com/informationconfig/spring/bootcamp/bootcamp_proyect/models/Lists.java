package com.informationconfig.spring.bootcamp.bootcamp_proyect.models;

public class Lists {
    
    private String listId;
    private String name;
    private Task[] tasks;

    public String getListId() {
        return listId;
    }
    public void setListId(String listId) {
        this.listId = listId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Task[] getTasks() {
        return tasks;
    }
    public void setTasks(Task[] tasks) {
        this.tasks = tasks;
    }

}
