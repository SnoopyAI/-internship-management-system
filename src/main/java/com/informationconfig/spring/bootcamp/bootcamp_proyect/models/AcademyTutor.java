package com.informationconfig.spring.bootcamp.bootcamp_proyect.models;

public class AcademyTutor extends User {
    
        private String academy;
        private String department;
        private PerformanceReport[] reports;

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
        public PerformanceReport[] getReports() {
            return reports;
        }
        public void setReports(PerformanceReport[] reports) {
            this.reports = reports;
        }
        public void reviewReport(PerformanceReport report) {
            // Logic to review a performance report
        }
        public void evaluateIntern(Intern intern, PerformanceReport report) {
            // Logic to evaluate an intern
        }
        
}
