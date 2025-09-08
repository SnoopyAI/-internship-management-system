package com.informationconfig.spring.bootcamp.bootcamp_proyect.services;
import org.springframework.stereotype.Service;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.PerformanceReport;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.PerformanceReportRepository;
import java.util.ArrayList;

@Service
public class PerformanceReportService {
    
    private final PerformanceReportRepository performanceReportRepository;

    public PerformanceReportService(PerformanceReportRepository performanceReportRepository) {
        this.performanceReportRepository = performanceReportRepository;
    }

    public PerformanceReport addPerformanceReport(PerformanceReport performanceReport) {
        return performanceReportRepository.addPerformanceReport(performanceReport);
    }

    public ArrayList<PerformanceReport> getAllPerformanceReports() {
        return performanceReportRepository.getAllPerformanceReports();
    }

    public boolean deletePerformanceReport(String id) {
        return performanceReportRepository.deletePerformanceReport(id);
    }

    public PerformanceReport updatePerformanceReport(String id, PerformanceReport updatedPerformanceReport) {
        return performanceReportRepository.updatePerformanceReport(id, updatedPerformanceReport);
    }

    public PerformanceReport getPerformanceReportById(String id) {
        return performanceReportRepository.getPerformanceReportById(id);
    }

    public ArrayList<PerformanceReport> getAllPerformanceReports(ArrayList<PerformanceReport> performanceReports) {
        return performanceReportRepository.getAllPerformanceReports(performanceReports);
    }
}
