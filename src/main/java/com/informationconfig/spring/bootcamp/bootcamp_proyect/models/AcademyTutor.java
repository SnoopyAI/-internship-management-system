package com.informationconfig.spring.bootcamp.bootcamp_proyect.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "academy_tutors")

public class AcademyTutor extends User {

        
        @Column(name = "Academy", length = 100, nullable = false)
        private String academy;

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

        // Constructors

        public AcademyTutor() {}

        public AcademyTutor(Integer id, String name, String email, String password, String academy, String department){
            super(id, name, email, password);
            this.academy = academy;
            this.department = department;
        }

        // Getters and Setters

        public List<Task> getCreatedTasks() {
            return createdTasks;
        }
        public void setCreatedTasks(List<Task> createdTasks) {
            this.createdTasks = createdTasks;
        }
        
        public String getAcademy() {
            return academy;
        }
        public void setAcademy(String academy) {
            this.academy = academy;
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
  
}
