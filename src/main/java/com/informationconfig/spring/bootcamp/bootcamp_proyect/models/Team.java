package com.informationconfig.spring.bootcamp.bootcamp_proyect.models;

public class Team {
    
    private String teamId;
    private String name;
    private Intern[] members;
    private AcademyTutor academyTutor;
    private Task[] task;

    public String getTeamId() {
        return teamId;
    }
    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Intern[] getMembers() {
        return members;
    }
    public void setMembers(Intern[] members) {
        this.members = members;
    }
    public AcademyTutor getAcademyTutor() {
        return academyTutor;
    }
    public void setAcademyTutor(AcademyTutor academyTutor) {
        this.academyTutor = academyTutor;
    }
    public Task[] getLists() {
        return task;
    }
    public void setLists(Task[] task) {
        this.task = task;
    }
    public void addMember(Intern intern) {
        // Logic to add a member to the team
    }
    public void removeMember(String internId) {
        // Logic to remove a member from the team
    }

    
}
