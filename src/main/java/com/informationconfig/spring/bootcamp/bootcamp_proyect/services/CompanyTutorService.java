package com.informationconfig.spring.bootcamp.bootcamp_proyect.services;
import org.springframework.stereotype.Service;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.CompanyTutor;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.CompanyTutorRepository;
import java.util.ArrayList;

@Service
public class CompanyTutorService {
    
    private final CompanyTutorRepository companyTutorRepository;

    public CompanyTutorService(CompanyTutorRepository companyTutorRepository) {
        this.companyTutorRepository = companyTutorRepository;
    }

    public CompanyTutor addCompanyTutor(CompanyTutor companyTutor) {
        return companyTutorRepository.addCompanyTutor(companyTutor);
    }

    public ArrayList<CompanyTutor> getAllCompanyTutors() {
        return companyTutorRepository.getAllCompanyTutors();
    }

    public boolean deleteCompanyTutor(String id) {
        return companyTutorRepository.deleteCompanyTutor(id);
    }

    public CompanyTutor updateCompanyTutor(String id, CompanyTutor updatedCompanyTutor) {
        return companyTutorRepository.updateCompanyTutor(id, updatedCompanyTutor);
    }

    public CompanyTutor getCompanyTutorById(String id) {
        return companyTutorRepository.getCompanyTutorById(id);
    }

    public ArrayList<CompanyTutor> getAllCompanyTutors(ArrayList<CompanyTutor> companyTutors) {
        return companyTutorRepository.getAllCompanyTutors(companyTutors);
    }
}
