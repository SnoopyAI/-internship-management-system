package com.informationconfig.spring.bootcamp.bootcamp_proyect.services;
import org.springframework.stereotype.Service;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Intern;    
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.InternRepository;
import java.util.ArrayList;

@Service
public class InternService {
    
    private final InternRepository internRepository;

    public InternService(InternRepository internRepository) {
        this.internRepository = internRepository;
    }

    public Intern addIntern(Intern intern) {
        return internRepository.addIntern(intern);
    }

    public ArrayList<Intern> getAllInterns() {
        return internRepository.getAllInterns();
    }

    public boolean deleteIntern(String id) {
        return internRepository.deleteIntern(id);
    }

    public Intern updateIntern(String id, Intern updatedIntern) {
        return internRepository.updateIntern(id, updatedIntern);
    }

    public Intern getInternById(String id) {
        return internRepository.getInternById(id);
    }

    public ArrayList<Intern> getAllInterns(ArrayList<Intern> interns) {
        return internRepository.getAllInterns(interns);
    }
}
