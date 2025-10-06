package com.informationconfig.spring.bootcamp.bootcamp_proyect.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "boards")
public class Board {

    @Id
    @Column(name = "board_id", length = 50, nullable = false)
    private String boardId;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = true)
    private LocalDate endDate;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lists> lists = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Intern> interns = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "academy_tutor_id", nullable = false, foreignKey = @ForeignKey(name = "FK_ACADEMY_TUTOR_BOARD"))
    private AcademyTutor academyTutor;

    @ManyToOne
    @JoinColumn(name = "company_tutor_id", nullable = false, foreignKey = @ForeignKey(name = "FK_COMPANY_TUTOR_BOARD"))
    private CompanyTutor companyTutor;

    // Constructors

    public Board() {}

    public Board(String id, String name, String description, LocalDate startdate, LocalDate endDate) {
        boardId = id;
        this.name = name;
        this.description = description;
        this.startDate = startdate;
        this.endDate = endDate;
    } 

    // Getters y Setters
    public String getBoardId() { return boardId; }
    public void setBoardId(String boardId) { this.boardId = boardId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public List<Lists> getLists() { return lists; }
    public void setLists(List<Lists> lists) { this.lists = lists; }

    public List<Intern> getInterns() { return interns; }
    public void setInterns(List<Intern> interns) { this.interns = interns; }

    public AcademyTutor getAcademyTutor() { return academyTutor; }
    public void setAcademyTutor(AcademyTutor academyTutor) { this.academyTutor = academyTutor; }

    public CompanyTutor getCompanyTutor() { return companyTutor; }
    public void setCompanyTutor(CompanyTutor companyTutor) { this.companyTutor = companyTutor; }

     // Métodos específicos

    public void createList(Lists list) {
        list.setBoard(this);
        this.lists.add(list);
    }

    public void deleteList(String listId) {
        this.lists.removeIf(l -> l.getListId().equals(listId));
    }

    public void addIntern(Intern intern) {
        intern.setBoard(this);
        this.interns.add(intern);
    }

    public void removeIntern(String internId) {
        this.interns.removeIf(i -> i.getId().equals(internId));
    }
}
