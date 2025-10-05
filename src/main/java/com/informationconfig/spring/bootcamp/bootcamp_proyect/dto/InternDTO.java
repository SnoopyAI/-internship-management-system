package com.informationconfig.spring.bootcamp.bootcamp_proyect.dto;

public class InternDTO {
    private String id;
    private String name;
    private String email;
    private String password;
    private String university;
    private String career;
    private int semester;
    private String academyTutorId;
    private String companyTutorId;
    private String boardId;
    
    public InternDTO(String id, String name, String email, String password, String university, String career,
            int semester, String academyTutorId, String companyTutorId, String boardId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.university = university;
        this.career = career;
        this.semester = semester;
        this.academyTutorId = academyTutorId;
        this.companyTutorId = companyTutorId;
        this.boardId = boardId;
    }

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

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getAcademyTutorId() {
        return academyTutorId;
    }

    public void setAcademyTutorId(String academyTutorId) {
        this.academyTutorId = academyTutorId;
    }

    public String getCompanyTutorId() {
        return companyTutorId;
    }

    public void setCompanyTutorId(String companyTutorId) {
        this.companyTutorId = companyTutorId;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    
}

