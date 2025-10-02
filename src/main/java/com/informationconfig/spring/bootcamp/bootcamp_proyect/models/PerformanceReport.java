package com.informationconfig.spring.bootcamp.bootcamp_proyect.models;
import jakarta.persistence.*;



@Entity
@Table(name = "performance_reports")
@DiscriminatorValue("PERFORMANCE_REPORT")

public class PerformanceReport {
    
    @Id
    @Column(name = "ID", length = 50, nullable = false)
    private String reportId;

    @Column(name = "Date", length = 50, nullable = false)
    private String reportDate;

    @Column(name = "Content", length = 500, nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "academy_tutor_id", nullable = false, foreignKey = @ForeignKey(name = "FK_ACADEMY_TUTOR_REPORT"))
    private AcademyTutor academyTutor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_tutor_id", nullable = false, foreignKey = @ForeignKey(name = "FK_COMPANY_TUTOR_REPORT"))
    private CompanyTutor companyTutor;


    // Métodos específicos de PerformanceReport

    public void generateReport() {
        // Logic to generate performance report
    }
    public void submitReport() {
        // Logic to submit the report
    }
    public void viewReport() {
        // Logic to review the report
    }

    // Getters and setters

    public String getReportId() {
        return reportId;
    }
    public void setReportId(String reportId) {
        this.reportId = reportId;
    }
    public String getReportDate() {
        return reportDate;
    }
    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    
}
