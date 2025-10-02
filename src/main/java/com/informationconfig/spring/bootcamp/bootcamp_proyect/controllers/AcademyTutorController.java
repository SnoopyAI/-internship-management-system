package com.informationconfig.spring.bootcamp.bootcamp_proyect.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.AcademyTutor;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.services.AcademyTutorService;
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
@RequestMapping("/academytutors")
public class AcademyTutorController {
    
    private final AcademyTutorService academyTutorService;

    public AcademyTutorController(AcademyTutorService academyTutorService) {
        this.academyTutorService = academyTutorService;
    }

    @PostMapping("/add")
    public AcademyTutor add(@Valid @RequestBody AcademyTutor academyTutor) {
        return this.academyTutorService.addAcademyTutor(academyTutor);
    }

    @PostMapping("/createVariable")
    public ArrayList<AcademyTutor> createVariable(@RequestBody ArrayList<AcademyTutor> academyTutors) {
        return this.academyTutorService.getAllAcademyTutors(academyTutors);
    }
    
    @GetMapping("/ReadAll")
    public ArrayList<AcademyTutor> getAllAcademyTutors() {
        return this.academyTutorService.getAllAcademyTutors();
    }

      @GetMapping("/{id}")
    public AcademyTutor getAcademyTutorById(@PathVariable String id) {
        return this.academyTutorService.getAcademyTutorById(id);
    }

    @PatchMapping("/{id}")
    public AcademyTutor updateAcademyTutor(@PathVariable String id, @Valid @RequestBody AcademyTutor updatedAcademyTutor) {
        return this.academyTutorService.updateAcademyTutor(id, updatedAcademyTutor);
    }
    
    @DeleteMapping("/{id}")
    public boolean deleteAcademyTutor(@PathVariable String id) {
        return this.academyTutorService.deleteAcademyTutor(id);
    }
}
