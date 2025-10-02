package com.informationconfig.spring.bootcamp.bootcamp_proyect.repository;  
import java.util.ArrayList;
import org.springframework.stereotype.Repository;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Intern;

@Repository
public class InternRepository {
    
    private ArrayList<Intern> interns = new ArrayList<>();

    public Intern addIntern(Intern intern) {
        interns.add(intern);
        return intern;
    }

    public ArrayList<Intern> getAllInterns(ArrayList<Intern> interns) {
        interns.addAll(interns);
        return interns;
    }

    public boolean deleteIntern(String id) {
        for (int i = 0; i < interns.size(); i++) {
            if (interns.get(i).getId() == id) {
                interns.remove(i);
                return true;
            }
        }
        return false;
    }

    public Intern updateIntern(String id, Intern updatedIntern) {
        for (int i = 0; i < interns.size(); i++) {
            if (interns.get(i).getId() == id) {
                interns.set(i, updatedIntern);
                return updatedIntern;
            }
        }
        return null; // or throw an exception if preferred
    }

    public Intern getInternById(String id) {
        for (Intern intern : interns) {
            if (intern.getId() == id) {
                return intern;
            }
        }
        return null; // or throw an exception if preferred
    }

    public ArrayList<Intern> getAllInterns() {
        return interns;
    }

}
