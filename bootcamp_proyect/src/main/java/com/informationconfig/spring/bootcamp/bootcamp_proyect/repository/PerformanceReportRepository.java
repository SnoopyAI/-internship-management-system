package com.informationconfig.spring.bootcamp.bootcamp_proyect.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.PerformanceReport;

@Repository
public interface PerformanceReportRepository extends JpaRepository<PerformanceReport, Integer> {}
