package com.informationconfig.spring.bootcamp.bootcamp_proyect.repository;
import org.springframework.stereotype.Repository;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.AcademyTutor;
import java.util.ArrayList;

@Repository
public class AcademyTutorRepository {
    
    private ArrayList<AcademyTutor> academyTutors = new ArrayList<>();

    public AcademyTutor addAcademyTutor(AcademyTutor academyTutor) {
        academyTutors.add(academyTutor);
        return academyTutor;
    }

    public ArrayList<AcademyTutor> getAllAcademyTutors(ArrayList<AcademyTutor> academyTutors) {
        academyTutors.addAll(academyTutors);
        return academyTutors;
    }

    public boolean deleteAcademyTutor(String id) {
        for (int i = 0; i < academyTutors.size(); i++) {
            if (academyTutors.get(i).getId() == id) {
                academyTutors.remove(i);
                return true;
            }
        }
        return false;
    }

    public AcademyTutor updateAcademyTutor(String id, AcademyTutor updatedAcademyTutor) {
        for (int i = 0; i < academyTutors.size(); i++) {
            if (academyTutors.get(i).getId().equals(id)) {
                academyTutors.set(i, updatedAcademyTutor);
                return updatedAcademyTutor;
            }
        }
        return null; // or throw an exception if preferred
    }

    public AcademyTutor getAcademyTutorById(String id) {
        for (AcademyTutor academyTutor : academyTutors) {
            if (academyTutor.getId().equals(id)) {
                return academyTutor;
            }
        }
        return null; // or throw an exception if preferred
    }

    public ArrayList<AcademyTutor> getAllAcademyTutors() {
        return academyTutors;
    }
}
