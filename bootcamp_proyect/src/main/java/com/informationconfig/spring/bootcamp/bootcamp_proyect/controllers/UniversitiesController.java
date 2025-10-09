package com.informationconfig.spring.bootcamp.bootcamp_proyect.controllers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.informationconfig.spring.bootcamp.bootcamp_proyect.dto.UniversityDTO;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Universities;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.services.UniversityService;

import java.util.List;
import java.util.Optional;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/universities")
public class UniversitiesController {

    private final UniversityService universityService;

    public UniversitiesController(UniversityService universityService) {
        this.universityService = universityService;
    }

    @PostMapping("/add")
    public UniversityDTO add(@RequestBody UniversityDTO dto) {
        Universities university = universityService.addUniversity(dto);
        return new UniversityDTO(

            university.getName()
        );
    }

    @GetMapping("/ReadAll")
    public List<UniversityDTO> getAllUniversities() {
        List<Universities> universities = universityService.getAllUniversities();
        return universities.stream().map(u -> new UniversityDTO(
            u.getUniversityId(),
            u.getName()
        )).toList();
    }

    @GetMapping("/{id}")
    public UniversityDTO getUniversityById(@PathVariable Integer id) {
        Optional<Universities> universityOpt = universityService.getUniversityById(id);
        if (universityOpt.isPresent()) {
            Universities u = universityOpt.get();
            return new UniversityDTO(u.getUniversityId(), u.getName());
        } else {
            return null; // O manejar el caso cuando no se encuentra la universidad
        }
    }

    @PatchMapping("/{id}")
    public UniversityDTO updateUniversity(@PathVariable Integer id, @RequestBody UniversityDTO dto) {
        Universities existing = this.universityService.updateUniversity(id, dto);
        if (existing == null) {
            return null; // O manejar el caso cuando no se encuentra la universidad
        }

        Universities updated = this.universityService.updateUniversity(id, dto);
        return new UniversityDTO(

            updated.getName()
        );
    }

    @DeleteMapping("/{id}")
    public boolean deleteUniversity(@PathVariable Integer id) {
        return this.universityService.deleteUniversity(id);
    }
}
