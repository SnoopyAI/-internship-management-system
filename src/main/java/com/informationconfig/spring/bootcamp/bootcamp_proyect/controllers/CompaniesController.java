package com.informationconfig.spring.bootcamp.bootcamp_proyect.controllers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.informationconfig.spring.bootcamp.bootcamp_proyect.dto.CompaniesDTO;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Companies;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.services.CompaniesService;

import java.util.List;
import java.util.Optional;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/companies")
public class CompaniesController {
    
    private final CompaniesService companiesService;

    public CompaniesController(CompaniesService companiesService) {
        this.companiesService = companiesService;
    }

    @PostMapping("/add")
    public CompaniesDTO add(@RequestBody CompaniesDTO dto) {
        Companies company = companiesService.addCompany(dto);
        return new CompaniesDTO(

            company.getName(),
            company.getAddress(),
            company.getNit(),
            company.getEmail(),
            company.getPhone()

        );
    }

    @GetMapping("/ReadAll")
    public List<CompaniesDTO> getAllCompanies() {
        List<Companies> companies = companiesService.getAllCompanies();
        return companies.stream().map(c -> new CompaniesDTO(
            c.getCompanyId(),
            c.getName(),
            c.getAddress(),
            c.getNit(),
            c.getPhone(),
            c.getEmail()
        )).toList();
    }

    @GetMapping("/{id}")
    public CompaniesDTO getCompanyById(@PathVariable Integer id) {
        Optional<Companies> companyOpt = companiesService.getCompanyById(id);
        if (companyOpt.isPresent()) {
            Companies c = companyOpt.get();
            return new CompaniesDTO(c.getCompanyId(), c.getName(), c.getAddress(), c.getNit(), c.getPhone(), c.getEmail());
        } else {
            return null; // O manejar el caso cuando no se encuentra la empresa
        }
    }

    @PatchMapping("/{id}")
    public CompaniesDTO updateCompany(@PathVariable Integer id, @RequestBody CompaniesDTO dto) {
        Companies updatedCompany = companiesService.updateCompany(id, dto);
        if (updatedCompany != null) {
            return new CompaniesDTO(
                
                updatedCompany.getName(),
                updatedCompany.getAddress(),
                updatedCompany.getNit(),
                updatedCompany.getPhone(),
                updatedCompany.getEmail()
            );
        } else {
            return null; // O manejar el caso cuando no se encuentra la empresa
        }
    }

    @DeleteMapping("/{id}")
    public boolean deleteCompany(@PathVariable Integer id) {
        return this.companiesService.deleteCompany(id);
    }
}
