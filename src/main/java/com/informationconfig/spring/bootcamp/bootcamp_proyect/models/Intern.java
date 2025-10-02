package com.informationconfig.spring.bootcamp.bootcamp_proyect.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "interns")
public class Intern extends User {

    @Column(name = "university", length = 50, nullable = false)
    private String university; // Nombre simplificado

    @Column(name = "career", length = 100)
    private String career;
    
    @Column(name = "semester")
    private Integer semester;

    @ManyToMany(mappedBy = "interns")
    private List<Task> tasks = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false, foreignKey = @ForeignKey(name = "FK_BOARD_INTERN"))
    private Board board;

    @ManyToOne
    @JoinColumn(name = "academy_tutor_id")
    private AcademyTutor academyTutor;

    @ManyToOne
    @JoinColumn(name = "company_tutor_id") // nombre de la FK en la tabla de interns
    private CompanyTutor companyTutor;


    // Constructores
    public Intern() {}

    public Intern(String id, String name, String email, String password, String university) {
        super(id, name, email, password);
        this.university = university;
    }

    public Intern(String id, String name, String email, String password, 
                 String university, String career, Integer semester) {
        this(id, name, email, password, university);
        this.career = career;
        this.semester = semester;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
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


}