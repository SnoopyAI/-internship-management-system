package com.informationconfig.spring.bootcamp.bootcamp_proyect.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "interns")
public class Intern extends User {

    

    @Column(name = "career", length = 100, nullable = false)
    private String career;
    
    @Column(name = "semester", nullable = false)
    private Integer semester;

    @ManyToMany(mappedBy = "interns")
    private List<Task> tasks = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false, foreignKey = @ForeignKey(name = "FK_BOARD_INTERN"))
    private Board board;

    @ManyToOne
    @JoinColumn(name = "academy_tutor_id", nullable = false, foreignKey = @ForeignKey(name = "FK_BOARD_INTERN"))
    private AcademyTutor academyTutor;

    @ManyToOne
    @JoinColumn(name = "company_tutor_id", nullable = false, foreignKey = @ForeignKey(name = "FK_COMPANY_TUTOR_INTERN"))
    private CompanyTutor companyTutor;

    @ManyToOne
    @JoinColumn(name = "university_id", nullable = false, foreignKey = @ForeignKey(name = "FK_UNIVERSITY_INTERN"))
    private Universities university;


    // Constructores
    public Intern() {}

    public Intern(Integer id, String name, String email, String password, String career, Integer semester) {
        super(id, name, email, password);
        this.career = career;
        this.semester = semester;
    }


    // Getters y Setters
    
    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
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

    public AcademyTutor getAcademyTutor() {
        return academyTutor;
    }

    public void setAcademyTutor(AcademyTutor academyTutor) {
        this.academyTutor = academyTutor;
    }

    public CompanyTutor getCompanyTutor() {
        return companyTutor;
    }

    public void setCompanyTutor(CompanyTutor companyTutor) {
        this.companyTutor = companyTutor;
    }

    public Universities getUniversity() {
        return university;
    }

    public void setUniversity(Universities university) {
        this.university = university;
    }

}