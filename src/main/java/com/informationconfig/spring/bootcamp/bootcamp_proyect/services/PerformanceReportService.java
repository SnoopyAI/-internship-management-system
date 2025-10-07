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

    // Solo crear (no actualizar)
    public PerformanceReport addReport(ReportsDTO dto) {
        // Validar que el reporte no exista
        if (dto.getId() != null && performanceReportRepository.existsById(dto.getId())) {
            throw new RuntimeException("Ya existe un reporte con el ID: " + dto.getId());
        }
        
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
    public Optional<PerformanceReport> getReportById(Integer id) {
        return performanceReportRepository.findById(id);
    }

    // Actualizar un reporte
    public PerformanceReport updateReport(Integer id, ReportsDTO dto) {
        if (performanceReportRepository.existsById(id)) {
            PerformanceReport report = performanceReportRepository.findById(id).get();
            
            // Actualizar solo los campos no nulos del DTO
            if (dto.getReportDate() != null) {
                report.setReportDate(dto.getReportDate());
            }
            if (dto.getContent() != null) {
                report.setContent(dto.getContent());
            }
            // Note: Tutor relationships should be updated through dedicated endpoints
            
            return performanceReportRepository.save(report);
        }
        return null;
    }

    // Eliminar un reporte
    public boolean deleteReport(Integer id) {
        if (performanceReportRepository.existsById(id)) {
            performanceReportRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
