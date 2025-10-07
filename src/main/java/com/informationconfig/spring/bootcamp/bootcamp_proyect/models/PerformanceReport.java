package com.informationconfig.spring.bootcamp.bootcamp_proyect.models;
import java.time.LocalDate;

import jakarta.persistence.*;



@Entity
@Table(name = "performance_reports")
@DiscriminatorValue("PERFORMANCE_REPORT")

public class PerformanceReport {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "ID", length = 50, nullable = false)
    private Integer reportId;

    @Column(name = "Date", length = 50, nullable = false)
    private LocalDate reportDate;

    @Column(name = "Content", length = 500, nullable = true)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "academy_tutor_id", nullable = false, foreignKey = @ForeignKey(name = "FK_ACADEMY_TUTOR_REPORT"))
    private AcademyTutor academyTutor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_tutor_id", nullable = false, foreignKey = @ForeignKey(name = "FK_COMPANY_TUTOR_REPORT"))
    private CompanyTutor companyTutor;

    //

    public PerformanceReport() {}

    public PerformanceReport(Integer id, LocalDate reportDate, String content) {
        reportId = id;
        this.reportDate = reportDate;
        this.content = content;
    }

    // Getters and setters

    public Integer getReportId() {
        return reportId;
    }
    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }
    public LocalDate getReportDate() {
        return reportDate;
    }
    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    
    // Métodos específicos de PerformanceReport

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

    public void generateReport() {
        // Logic to generate performance report
    }
    public void submitReport() {
        // Logic to submit the report
    }
    public void viewReport() {
        // Logic to review the report
    }

    
}
