package com.informationconfig.spring.bootcamp.bootcamp_proyect.repository;
import org.springframework.stereotype.Repository;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.PerformanceReport;
import java.util.ArrayList;

@Repository
public class PerformanceReportRepository {
    
    private ArrayList<PerformanceReport> performanceReports = new ArrayList<>();

    public PerformanceReport addPerformanceReport(PerformanceReport performanceReport) {
        performanceReports.add(performanceReport);
        return performanceReport;
    }

    public ArrayList<PerformanceReport> getAllPerformanceReports(ArrayList<PerformanceReport> performanceReports) {
        performanceReports.addAll(performanceReports);
        return performanceReports;
    }

    public boolean deletePerformanceReport(String id) {
        for (int i = 0; i < performanceReports.size(); i++) {
            if (performanceReports.get(i).getReportId() == id) {
                performanceReports.remove(i);
                return true;
            }
        }
        return false;
    }

    public PerformanceReport updatePerformanceReport(String id, PerformanceReport updatedPerformanceReport) {
        for (int i = 0; i < performanceReports.size(); i++) {
            if (performanceReports.get(i).getReportId().equals(id)) {
                performanceReports.set(i, updatedPerformanceReport);
                return updatedPerformanceReport;
            }
        }
        return null; // or throw an exception if preferred
    }

    public PerformanceReport getPerformanceReportById(String id) {
        for (PerformanceReport performanceReport : performanceReports) {
            if (performanceReport.getReportId().equals(id)) {
                return performanceReport;
            }
        }
        return null; // or throw an exception if preferred
    }

    public ArrayList<PerformanceReport> getAllPerformanceReports() {
        return performanceReports;
    }
}
