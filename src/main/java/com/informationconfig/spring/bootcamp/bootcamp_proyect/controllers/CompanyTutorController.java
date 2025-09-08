package com.informationconfig.spring.bootcamp.bootcamp_proyect.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.CompanyTutor;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.services.CompanyTutorService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/companytutors")
public class CompanyTutorController {
    
    private final CompanyTutorService companyTutorService;

    public CompanyTutorController(CompanyTutorService companyTutorService) {
        this.companyTutorService = companyTutorService;
    }

    @PostMapping("/add")
    public CompanyTutor add(@Valid @RequestBody CompanyTutor companyTutor) {
        return this.companyTutorService.addCompanyTutor(companyTutor);
    }

    @PostMapping("/createVariable")
    public ArrayList<CompanyTutor> createVariable(@RequestBody ArrayList<CompanyTutor> companyTutors) {
        return this.companyTutorService.getAllCompanyTutors(companyTutors);
    }
    
    @GetMapping("/ReadAll")
    public ArrayList<CompanyTutor> getAllCompanyTutors() {
        return this.companyTutorService.getAllCompanyTutors();
    }

      @GetMapping("/{id}")
    public CompanyTutor getCompanyTutorById(@PathVariable String id) {
        return this.companyTutorService.getCompanyTutorById(id);
    }

    @PatchMapping("/{id}")
    public CompanyTutor updateCompanyTutor(@PathVariable String id, @Valid @RequestBody CompanyTutor updatedCompanyTutor) {
        return this.companyTutorService.updateCompanyTutor(id, updatedCompanyTutor);
    }
    
    @DeleteMapping("/{id}")
    public boolean deleteCompanyTutor(@PathVariable String id) {
        return this.companyTutorService.deleteCompanyTutor(id);
    }
}
