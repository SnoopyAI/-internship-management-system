package com.informationconfig.spring.bootcamp.bootcamp_proyect.services;

import org.springframework.stereotype.Service;

import com.informationconfig.spring.bootcamp.bootcamp_proyect.dto.CompanyTutorDTO;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.CompanyTutor;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.CompanyTutorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyTutorService {
    
    private final CompanyTutorRepository companyTutorRepository;

    public CompanyTutorService(CompanyTutorRepository companyTutorRepository) {
        this.companyTutorRepository = companyTutorRepository;
    }

    // Crear un nuevo CompanyTutor
    public CompanyTutor addCompanyTutor(CompanyTutorDTO dto) {
        CompanyTutor companyTutor = new CompanyTutor();
        companyTutor.setId(dto.getId());
        companyTutor.setName(dto.getName());
        companyTutor.setEmail(dto.getEmail());
        companyTutor.setPassword(dto.getPassword());
        companyTutor.setPosition(dto.getPosition());
        companyTutor.setCompany(dto.getCompany());
        
        return companyTutorRepository.save(companyTutor);
    }

    // Obtener todos los CompanyTutors
    public List<CompanyTutor> getAllCompanyTutors() {
        return companyTutorRepository.findAll();
    }

    // Obtener un CompanyTutor por ID
    public Optional<CompanyTutor> getCompanyTutorById(String id) {
        return companyTutorRepository.findById(id);
    }

    // Actualizar un CompanyTutor
    public CompanyTutor updateCompanyTutor(String id, CompanyTutor updatedCompanyTutor) {
        if (companyTutorRepository.existsById(id)) {
            updatedCompanyTutor.setId(id);
            return companyTutorRepository.save(updatedCompanyTutor);
        }
        return null;
    }

    // Eliminar un CompanyTutor
    public boolean deleteCompanyTutor(String id) {
        if (companyTutorRepository.existsById(id)) {
            companyTutorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
