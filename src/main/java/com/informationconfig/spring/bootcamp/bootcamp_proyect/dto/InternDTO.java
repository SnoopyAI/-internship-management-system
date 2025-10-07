package com.informationconfig.spring.bootcamp.bootcamp_proyect.dto;

public class InternDTO {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String university;
    private String career;
    private int semester;
    private Integer academyTutorId;
    private Integer companyTutorId;
    private Integer boardId;

    public InternDTO(String name, String email, String password, String university, String career,
            int semester, Integer academyTutorId, Integer companyTutorId, Integer boardId) {
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

    public InternDTO(Integer id, String name, String email, String university, String career,
            int semester, Integer academyTutorId, Integer companyTutorId, Integer boardId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.university = university;
        this.career = career;
        this.semester = semester;
        this.academyTutorId = academyTutorId;
        this.companyTutorId = companyTutorId;
        this.boardId = boardId;
    }

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

    public Integer getAcademyTutorId() {
        return academyTutorId;
    }

    public void setAcademyTutorId(Integer academyTutorId) {
        this.academyTutorId = academyTutorId;
    }

    public Integer getCompanyTutorId() {
        return companyTutorId;
    }

    public void setCompanyTutorId(Integer companyTutorId) {
        this.companyTutorId = companyTutorId;
    }

    public Integer getBoardId() {
        return boardId;
    }

    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    
}

