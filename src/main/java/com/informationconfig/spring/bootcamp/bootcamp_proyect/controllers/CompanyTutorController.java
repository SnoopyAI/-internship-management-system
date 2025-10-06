package com.informationconfig.spring.bootcamp.bootcamp_proyect.controllers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.informationconfig.spring.bootcamp.bootcamp_proyect.dto.CompanyTutorDTO;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.CompanyTutor;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.services.CompanyTutorService;

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
@RequestMapping("/companytutors")
public class CompanyTutorController {
    
    private final CompanyTutorService companyTutorService;

    public CompanyTutorController(CompanyTutorService companyTutorService) {
        this.companyTutorService = companyTutorService;
    }

    @PostMapping("/add")
    public CompanyTutorDTO add(@RequestBody CompanyTutorDTO dto) {
        CompanyTutor companyTutor = companyTutorService.addCompanyTutor(dto);
        return new CompanyTutorDTO(
            companyTutor.getId(),
            companyTutor.getName(),
            companyTutor.getEmail(),
            companyTutor.getPassword(),
            companyTutor.getPosition(),
            companyTutor.getCompany()
        );
    }

    @PostMapping("/createVariable")
    public List<CompanyTutor> createVariable(@RequestBody List<CompanyTutor> companyTutors) {
        return this.companyTutorService.getAllCompanyTutors();
    }
    
    @GetMapping("/ReadAll")
    public List<CompanyTutorDTO> getAllCompanyTutors() {
        List<CompanyTutor> tutors = companyTutorService.getAllCompanyTutors();
        return tutors.stream().map(t -> new CompanyTutorDTO(
                    t.getId(),
                    t.getName(),
                    t.getEmail(),
                    t.getPassword(),
                    t.getPosition(),
                    t.getCompany()
            )).toList();
    }

    @GetMapping("/{id}")
    public Optional<CompanyTutorDTO> getCompanyTutorById(@PathVariable String id) {
        return companyTutorService.getCompanyTutorById(id)
            .map(t -> new CompanyTutorDTO(
                    t.getId(),
                    t.getName(),
                    t.getEmail(),
                    t.getPassword(),
                    t.getPosition(),
                    t.getCompany()
            ));
    }

    @PatchMapping("/{id}")
    public CompanyTutor updateCompanyTutor(@PathVariable String id, @Valid @RequestBody CompanyTutor updatedCompanyTutor) {
        CompanyTutor existing = this.companyTutorService.getCompanyTutorById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        updatedCompanyTutor.setId(id);
        return this.companyTutorService.updateCompanyTutor(id, updatedCompanyTutor);
    }
    
    @DeleteMapping("/{id}")
    public boolean deleteCompanyTutor(@PathVariable String id) {
        return this.companyTutorService.deleteCompanyTutor(id);
    }
}
