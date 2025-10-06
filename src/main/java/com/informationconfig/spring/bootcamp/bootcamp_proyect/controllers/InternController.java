package com.informationconfig.spring.bootcamp.bootcamp_proyect.controllers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.informationconfig.spring.bootcamp.bootcamp_proyect.dto.InternDTO;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Intern;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.services.InternService;

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
@RequestMapping("/interns")
public class InternController {
    
    private final InternService internService;

    public InternController(InternService internService) {
        this.internService = internService;
    }

    @PostMapping("/add")
    public InternDTO add(@Valid @RequestBody InternDTO dto) {
    Intern intern = internService.addIntern(dto);
        return new InternDTO(
            intern.getId(),
            intern.getName(),
            intern.getEmail(),
            intern.getPassword(),
            intern.getUniversity(),
            intern.getCareer(),
            intern.getSemester(),
            intern.getAcademyTutor() != null ? intern.getAcademyTutor().getId() : null,
            intern.getCompanyTutor() != null ? intern.getCompanyTutor().getId() : null,
            intern.getBoard() != null ? intern.getBoard().getBoardId() : null
        );
}


    @PostMapping("/createVariable")
    public List<Intern> createVariable(@RequestBody List<Intern> interns) {
        return this.internService.getAllInterns();
    }
    
    @GetMapping("/ReadAll")
    public List<InternDTO>tAllInterns() {
    List<Intern> intern = internService.getAllInterns();
        return intern.stream().map(interns -> new InternDTO(
            interns.getId(),
            interns.getName(),
            interns.getEmail(),
            interns.getPassword(),
            interns.getUniversity(),
            interns.getCareer(),
            interns.getSemester(),
            interns.getAcademyTutor() != null ? interns.getAcademyTutor().getId() : null,
            interns.getCompanyTutor() != null ? interns.getCompanyTutor().getId() : null,
            interns.getBoard() != null ? interns.getBoard().getBoardId()  : null
        )).toList();
    }

      @GetMapping("/{id}")
    public Optional<InternDTO> getInternById(@PathVariable String id) {
        return internService.getInternById(id)
            .map(intern -> new InternDTO(
                intern.getId(),
                intern.getName(),
                intern.getEmail(),
                intern.getPassword(),
                intern.getUniversity(),
                intern.getCareer(),
                intern.getSemester(),
                intern.getAcademyTutor() != null ? intern.getAcademyTutor().getId() : null,
                intern.getCompanyTutor() != null ? intern.getCompanyTutor().getId() : null,
                intern.getBoard() != null ? intern.getBoard().getBoardId() : null
            ));
    }

    @PatchMapping("/{id}")
    public InternDTO updateIntern(@PathVariable String id, @Valid @RequestBody InternDTO dto) {
        Intern existing = this.internService.getInternById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        
        Intern updated = this.internService.updateIntern(id, dto);
        return new InternDTO(
            updated.getId(),
            updated.getName(),
            updated.getEmail(),
            updated.getPassword(),
            updated.getUniversity(),
            updated.getCareer(),
            updated.getSemester(),
            updated.getAcademyTutor() != null ? updated.getAcademyTutor().getId() : null,
            updated.getCompanyTutor() != null ? updated.getCompanyTutor().getId() : null,
            updated.getBoard() != null ? updated.getBoard().getBoardId() : null
        );
    }
    
    @DeleteMapping("/{id}")
    public boolean deleteIntern(@PathVariable String id) {
        return this.internService.deleteIntern(id);
    }

}
