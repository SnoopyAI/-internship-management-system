package com.informationconfig.spring.bootcamp.bootcamp_proyect.controllers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.AcademyTutor;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.dto.AcademyTutorDTO;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.services.AcademyTutorService;
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
@RequestMapping("/academytutors")
public class AcademyTutorController {
    
    private final AcademyTutorService academyTutorService;

    public AcademyTutorController(AcademyTutorService academyTutorService) {
        this.academyTutorService = academyTutorService;
    }

    @PostMapping("/add")
    public AcademyTutorDTO add(@Valid @RequestBody AcademyTutorDTO dto) {
    AcademyTutor academyTutor = academyTutorService.addAcademyTutor(dto);
        return new AcademyTutorDTO(
            academyTutor.getId(),
            academyTutor.getName(),
            academyTutor.getEmail(),
            academyTutor.getPassword(),
            academyTutor.getAcademy(),
            academyTutor.getDepartment()
        );
    }

    @PostMapping("/createVariable")
    public List<AcademyTutor> createVariable(@RequestBody List<AcademyTutor> academyTutors) {
        return this.academyTutorService.getAllAcademyTutors();
    }
    
    @GetMapping("/ReadAll")
    public List<AcademyTutorDTO> getAllAcademyTutors() {
        List<AcademyTutor> tutors = academyTutorService.getAllAcademyTutors();
        return tutors.stream().map(t -> new AcademyTutorDTO(
                    t.getId(),
                    t.getName(),
                    t.getEmail(),
                    t.getPassword(),
                    t.getAcademy(),
                    t.getDepartment()
            )).toList();
    }


      @GetMapping("/{id}")
    public Optional<AcademyTutorDTO>tAcademyTutorById(@PathVariable String id) {
        return academyTutorService.getAcademyTutorById(id)
            .map(t -> new AcademyTutorDTO(
                    t.getId(),
                    t.getName(),
                    t.getEmail(),
                    t.getPassword(),
                    t.getAcademy(),
                    t.getDepartment()
            ));
    }

    @PatchMapping("/{id}")
    public AcademyTutorDTO updateAcademyTutor(@PathVariable String id, @Valid @RequestBody AcademyTutorDTO dto) {
        AcademyTutor existing = this.academyTutorService.getAcademyTutorById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        
        AcademyTutor updated = this.academyTutorService.updateAcademyTutor(id, dto);
        return new AcademyTutorDTO(
            updated.getId(),
            updated.getName(),
            updated.getEmail(),
            updated.getPassword(),
            updated.getAcademy(),
            updated.getDepartment()
        );
    }
    
    @DeleteMapping("/{id}")
    public boolean deleteAcademyTutor(@PathVariable String id) {
        return this.academyTutorService.deleteAcademyTutor(id);
    }
}
