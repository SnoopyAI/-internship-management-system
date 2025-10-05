package com.informationconfig.spring.bootcamp.bootcamp_proyect.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.informationconfig.spring.bootcamp.bootcamp_proyect.dto.ReportsDTO;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.PerformanceReport;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.AcademyTutorRepository;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.CompanyTutorRepository;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.PerformanceReportRepository;
import java.util.List;
import java.util.Optional;

@Service
public class PerformanceReportService {
    
    private final PerformanceReportRepository performanceReportRepository;

    public PerformanceReportService(PerformanceReportRepository performanceReportRepository) {
        this.performanceReportRepository = performanceReportRepository;
    }

    @Autowired
    private CompanyTutorRepository companyTutorRepository;

    @Autowired
    private AcademyTutorRepository academyTutorRepository;

    // Crear un nuevo reporte
    public PerformanceReport addReport(ReportsDTO dto) {
        PerformanceReport report =  new PerformanceReport();
        report.setContent(dto.getContent());
        report.setReportId(dto.getId());
        report.setReportDate(dto.getReportDate());

    if (dto.getAcademyTutorId() != null) {
        academyTutorRepository.findById(dto.getAcademyTutorId())
            .ifPresent(report::setAcademyTutor);
    }

    // Asociar companyTutor si existe
    if (dto.getCompanyTutorId() != null) {
        companyTutorRepository.findById(dto.getCompanyTutorId())
            .ifPresent(report::setCompanyTutor);
    }

        return performanceReportRepository.save(report);
    }
    // Obtener todas las listas
    public List<PerformanceReport> getAllReports() {
        return performanceReportRepository.findAll();
    }

    // Obtener un reporte por ID
    public Optional<PerformanceReport> getReportById(String id) {
        return performanceReportRepository.findById(id);
    }

    // Actualizar un reporte
    public PerformanceReport updateReport(String id, PerformanceReport updatedReport) {
        if (performanceReportRepository.existsById(id)) {
            updatedReport.setReportId(id);
            return performanceReportRepository.save(updatedReport);
        }
        return null;
    }

    // Eliminar un reporte
    public boolean deleteReport(String id) {
        if (performanceReportRepository.existsById(id)) {
            performanceReportRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
