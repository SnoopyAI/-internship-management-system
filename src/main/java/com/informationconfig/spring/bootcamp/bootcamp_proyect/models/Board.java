package com.informationconfig.spring.bootcamp.bootcamp_proyect.models;

public class Board {
    
    private String boardId;
    private String name;
    private String description;
    private String startDate;
    private List[] lists;
    private Intern[] interns;
    private AcademyTutor[] tutors;

    public String getBoardId() {
        return boardId;
    }
    public void setBoardId(String boardId) {
        this.boardId = boardId;
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
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public List[] getLists() {
        return lists;
    }
    public void setLists(List[] lists) {
        this.lists = lists;
    }
    public Intern[] getInterns() {
        return interns;
    }
    public void setInterns(Intern[] interns) {
        this.interns = interns;
    }
    public AcademyTutor[] getTutors() {
        return tutors;
    }
    public void setTutors(AcademyTutor[] tutors) {
        this.tutors = tutors;
    }
    public void createList(String listName) {
        // Logic to create a new list on the board
    }
    public void deleteList(String listId) {
        // Logic to delete a list from the board
    }
    public void addMember(String userId) {
        // Logic to add a member to the board
    }
    public void removeMember(String userId) {
        // Logic to remove a member from the board
    }
    public void assingGroup(String groupId) {
        // Logic to assign a group to the board
    }
}
