package com.informationconfig.spring.bootcamp.bootcamp_proyect.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "academy_tutors")

public class AcademyTutor extends User {


        @Column(name = "Department", length = 100, nullable = true)
        private String department;

        @OneToMany(mappedBy = "academyTutor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
        private List<PerformanceReport> reports = new ArrayList<>();

        @OneToMany(mappedBy = "academyTutor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
        private List<Intern> interns = new ArrayList<>();

        @OneToMany(mappedBy = "createdByTutorId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
        private List<Task> createdTasks = new ArrayList<>();

        @OneToMany(mappedBy = "academyTutor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
        private List<Board> boards = new ArrayList<>();

        @ManyToOne
        @JoinColumn(name = "university_id", nullable = false, foreignKey = @ForeignKey(name = "FK_UNIVERSITY_ACADEMY_TUTOR"))
        private Universities university;

        // Constructors

        public AcademyTutor() {}

        public AcademyTutor(Integer id, String name, String email, String password, String department){
            super(id, name, email, password);
            this.department = department;
        }

        // Getters and Setters

        public List<Task> getCreatedTasks() {
            return createdTasks;
        }
        public void setCreatedTasks(List<Task> createdTasks) {
            this.createdTasks = createdTasks;
        }
     
        public String getDepartment() {
            return department;
        }
        public void setDepartment(String department) {
            this.department = department;
        }
        public void reviewReport(PerformanceReport report) {
            // Logic to review a performance report
        }
        public void evaluateIntern(Intern intern, PerformanceReport report) {
            // Logic to evaluate an intern
        }
        public List<PerformanceReport> getReports() {
            return reports;
        }
        public void setReports(List<PerformanceReport> reports) {
            this.reports = reports;
        }
        public List<Intern> getInterns() {
            return interns;
        }
        public void setInterns(List<Intern> interns) {
            this.interns = interns;
        }
        public List<Board> getBoards() {
            return boards;
        }
        public void setBoards(List<Board> boards) {
            this.boards = boards;
        }

        public Universities getUniversity() {
            return university;
        }

        public void setUniversity(Universities university) {
            this.university = university;
        }
  
}
