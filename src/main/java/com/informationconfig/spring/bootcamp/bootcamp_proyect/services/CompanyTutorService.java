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

    // Solo crear (no actualizar)
    public CompanyTutor addCompanyTutor(CompanyTutorDTO dto) {
        // Validar que el tutor no exista
        if (dto.getId() != null && companyTutorRepository.existsById(dto.getId())) {
            throw new RuntimeException("Ya existe un tutor de empresa con el ID: " + dto.getId());
        }
        
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
    public Optional<CompanyTutor> getCompanyTutorById(Integer id) {
        return companyTutorRepository.findById(id);
    }

    // Actualizar un CompanyTutor
    public CompanyTutor updateCompanyTutor(Integer id, CompanyTutorDTO dto) {
        if (companyTutorRepository.existsById(id)) {
            CompanyTutor companyTutor = companyTutorRepository.findById(id).get();
            
            // Actualizar solo los campos no nulos del DTO
            if (dto.getName() != null) {
                companyTutor.setName(dto.getName());
            }
            if (dto.getEmail() != null) {
                companyTutor.setEmail(dto.getEmail());
            }
            if (dto.getPassword() != null) {
                companyTutor.setPassword(dto.getPassword());
            }
            if (dto.getPosition() != null) {
                companyTutor.setPosition(dto.getPosition());
            }
            if (dto.getCompany() != null) {
                companyTutor.setCompany(dto.getCompany());
            }
            
            return companyTutorRepository.save(companyTutor);
        }
        return null;
    }

    // Eliminar un CompanyTutor
    public boolean deleteCompanyTutor(Integer id) {
        if (companyTutorRepository.existsById(id)) {
            companyTutorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
