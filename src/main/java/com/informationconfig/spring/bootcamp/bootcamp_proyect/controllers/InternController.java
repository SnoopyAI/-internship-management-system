package com.informationconfig.spring.bootcamp.bootcamp_proyect.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Intern;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.services.InternService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/interns")
public class InternController {
    
    private final InternService internService;

    public InternController(InternService internService) {
        this.internService = internService;
    }

    @PostMapping("/add")
    public Intern add(@Valid @RequestBody Intern intern) {
        return this.internService.addIntern(intern);
    }

    @PostMapping("/createVariable")
    public ArrayList<Intern> createVariable(@RequestBody ArrayList<Intern> interns) {
        return this.internService.getAllInterns(interns);
    }
    
    @GetMapping("/ReadAll")
    public ArrayList<Intern> getAllInterns() {
        return this.internService.getAllInterns();
    }

      @GetMapping("/{id}")
    public Intern getInternById(@PathVariable String id) {
        return this.internService.getInternById(id);
    }

    @PatchMapping("/{id}")
    public Intern updateIntern(@PathVariable String id, @Valid @RequestBody Intern updatedIntern) {
        return this.internService.updateIntern(id, updatedIntern);
    }
    
    @DeleteMapping("/{id}")
    public boolean deleteIntern(@PathVariable String id) {
        return this.internService.deleteIntern(id);
    }

}
