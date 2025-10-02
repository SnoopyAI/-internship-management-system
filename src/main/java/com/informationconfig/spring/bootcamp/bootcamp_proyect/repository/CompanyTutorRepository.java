package com.informationconfig.spring.bootcamp.bootcamp_proyect.repository;
import org.springframework.stereotype.Repository;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.CompanyTutor;
import java.util.ArrayList;

@Repository
public class CompanyTutorRepository {
    
    private ArrayList<CompanyTutor> companyTutors = new ArrayList<>();

    public CompanyTutor addCompanyTutor(CompanyTutor companyTutor) {
        companyTutors.add(companyTutor);
        return companyTutor;
    }

    public ArrayList<CompanyTutor> getAllCompanyTutors(ArrayList<CompanyTutor> companyTutors) {
        companyTutors.addAll(companyTutors);
        return companyTutors;
    }

    public boolean deleteCompanyTutor(String id) {
        for (int i = 0; i < companyTutors.size(); i++) {
            if (companyTutors.get(i).getId() == id) {
                companyTutors.remove(i);
                return true;
            }
        }
        return false;
    }

    public CompanyTutor updateCompanyTutor(String id, CompanyTutor updatedCompanyTutor) {
        for (int i = 0; i < companyTutors.size(); i++) {
            if (companyTutors.get(i).getId() == id) {
                companyTutors.set(i, updatedCompanyTutor);
                return updatedCompanyTutor;
            }
        }
        return null; // or throw an exception if preferred
    }

    public CompanyTutor getCompanyTutorById(String id) {
        for (CompanyTutor companyTutor : companyTutors) {
            if (companyTutor.getId() == id) {
                return companyTutor;
            }
        }
        return null; // or throw an exception if preferred
    }

    public ArrayList<CompanyTutor> getAllCompanyTutors() {
        return companyTutors;
    }

}