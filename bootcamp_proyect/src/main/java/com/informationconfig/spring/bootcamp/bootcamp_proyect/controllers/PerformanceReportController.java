package com.informationconfig.spring.bootcamp.bootcamp_proyect.controllers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.informationconfig.spring.bootcamp.bootcamp_proyect.dto.ReportsDTO;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.PerformanceReport;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.services.PerformanceReportService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/reports")
public class PerformanceReportController {
    
    private final PerformanceReportService performanceReportService;

    public PerformanceReportController(PerformanceReportService performanceReportService) {
        this.performanceReportService = performanceReportService;
    }

    @PostMapping("/add")
    public ReportsDTO add(@Valid @RequestBody ReportsDTO dto) {
        PerformanceReport reports = performanceReportService.addReport(dto);
        return new ReportsDTO(

            reports.getReportDate(),
            reports.getContent(),
            reports.getAcademyTutor() != null ? reports.getAcademyTutor().getId() : null,
            reports.getCompanyTutor() != null ? reports.getCompanyTutor().getId() : null
            
        );
    }

    @PostMapping("/createVariable")
    public List<PerformanceReport> createVariable(@RequestBody List<PerformanceReport> Reports) {
        return this.performanceReportService.getAllReports();
    }
    
    @GetMapping("/ReadAll")
    public List<ReportsDTO> getAllPerformanceReports() {
        List<PerformanceReport> reports = performanceReportService.getAllReports();
        return reports.stream().map(report -> new ReportsDTO(

            report.getReportId(),
            report.getReportDate(),
            report.getContent(),
            report.getAcademyTutor() != null ? report.getAcademyTutor().getId() : null,
            report.getCompanyTutor() != null ? report.getCompanyTutor().getId() : null
        )).toList();
    }

    @GetMapping("/{id}")
    public Optional<ReportsDTO> getPerformanceReportById(@PathVariable Integer id) {
        return performanceReportService.getReportById(id)
        .map(r -> new ReportsDTO(

            r.getReportId(),
            r.getReportDate(),
            r.getContent(),
            r.getAcademyTutor() != null ? r.getAcademyTutor().getId() : null,
            r.getCompanyTutor() != null ? r.getCompanyTutor().getId() : null
        ));
    }

    @PatchMapping("/{id}")
    public ReportsDTO updatePerformanceReport(@PathVariable Integer id, @Valid @RequestBody ReportsDTO dto) {
        PerformanceReport existing = this.performanceReportService.getReportById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        
        PerformanceReport updated = this.performanceReportService.updateReport(id, dto);
        return new ReportsDTO(
            updated.getReportId(),
            updated.getReportDate(),
            updated.getContent(),
            updated.getAcademyTutor() != null ? updated.getAcademyTutor().getId() : null,
            updated.getCompanyTutor() != null ? updated.getCompanyTutor().getId() : null
        );
    }
    
    @DeleteMapping("/{id}")
    public boolean deletePerformanceReport(@PathVariable Integer id) {
        return this.performanceReportService.deleteReport(id);
    }
}
