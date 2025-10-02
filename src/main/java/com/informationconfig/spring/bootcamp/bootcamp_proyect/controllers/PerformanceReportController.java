package com.informationconfig.spring.bootcamp.bootcamp_proyect.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.PerformanceReport;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.services.PerformanceReportService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RestController
@RequestMapping("/performancereports")
public class PerformanceReportController {
    
    private final PerformanceReportService performanceReportService;

    public PerformanceReportController(PerformanceReportService performanceReportService) {
        this.performanceReportService = performanceReportService;
    }

    @PostMapping("/add")
    public PerformanceReport add(@Valid @RequestBody PerformanceReport performanceReport) {
        return this.performanceReportService.addPerformanceReport(performanceReport);
    }

    @PostMapping("/createVariable")
    public ArrayList<PerformanceReport> createVariable(@RequestBody ArrayList<PerformanceReport> performanceReports) {
        return this.performanceReportService.getAllPerformanceReports(performanceReports);
    }
    
    @GetMapping("/ReadAll")
    public ArrayList<PerformanceReport> getAllPerformanceReports() {
        return this.performanceReportService.getAllPerformanceReports();
    }

      @GetMapping("/{id}")
    public PerformanceReport getPerformanceReportById(@PathVariable String id) {
        return this.performanceReportService.getPerformanceReportById(id);
    }

    @PatchMapping("/{id}")
    public PerformanceReport updatePerformanceReport(@PathVariable String id, @Valid @RequestBody PerformanceReport updatedPerformanceReport) {
        return this.performanceReportService.updatePerformanceReport(id, updatedPerformanceReport);
    }
    
    @DeleteMapping("/{id}")
    public boolean deletePerformanceReport(@PathVariable String id) {
        return this.performanceReportService.deletePerformanceReport(id);
    }
}
