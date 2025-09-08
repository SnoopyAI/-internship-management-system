package com.informationconfig.spring.bootcamp.bootcamp_proyect.services;
import org.springframework.stereotype.Service;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.AcademyTutor;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.AcademyTutorRepository;
import java.util.ArrayList;

@Service
public class AcademyTutorService {
    
    private final AcademyTutorRepository academyTutorRepository;

    public AcademyTutorService(AcademyTutorRepository academyTutorRepository) {
        this.academyTutorRepository = academyTutorRepository;
    }

    public AcademyTutor addAcademyTutor(AcademyTutor academyTutor) {
        return academyTutorRepository.addAcademyTutor(academyTutor);
    }

    public ArrayList<AcademyTutor> getAllAcademyTutors() {
        return academyTutorRepository.getAllAcademyTutors();
    }

    public boolean deleteAcademyTutor(String id) {
        return academyTutorRepository.deleteAcademyTutor(id);
    }

    public AcademyTutor updateAcademyTutor(String id, AcademyTutor updatedAcademyTutor) {
        return academyTutorRepository.updateAcademyTutor(id, updatedAcademyTutor);
    }

    public AcademyTutor getAcademyTutorById(String id) {
        return academyTutorRepository.getAcademyTutorById(id);
    }

    public ArrayList<AcademyTutor> getAllAcademyTutors(ArrayList<AcademyTutor> academyTutors) {
        return academyTutorRepository.getAllAcademyTutors(academyTutors);
    }
}
