package com.informationconfig.spring.bootcamp.bootcamp_proyect.models;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;


@Entity
@Table(name = "universities")
public class Universities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, length = 50, unique = true)
    private Integer universityId;

    @Column(name = "name", nullable = false, length = 80)
    private String name;

    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AcademyTutor> tutors = new ArrayList<>();


    public Universities() {}

    public Universities(Integer universityId, String name) {
        this.universityId = universityId;
        this.name = name;
    }
    public Integer getUniversityId() {
        return universityId;
    }
    public void setUniversityId(Integer universityId) {
        this.universityId = universityId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<AcademyTutor> getTutors() {
        return tutors;
    }

    public void setTutors(List<AcademyTutor> tutors) {
        this.tutors = tutors;
    }

}
