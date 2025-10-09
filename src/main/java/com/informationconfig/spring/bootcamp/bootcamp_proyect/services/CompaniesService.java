package com.informationconfig.spring.bootcamp.bootcamp_proyect.services;

import com.informationconfig.spring.bootcamp.bootcamp_proyect.dto.CompaniesDTO;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Companies;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.CompaniesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CompaniesService {
  
    private final CompaniesRepository companiesRepository;

    public CompaniesService(CompaniesRepository companiesRepository) {
        this.companiesRepository = companiesRepository;
    }

    public Companies addCompany(CompaniesDTO dto) {
        
        if (dto.getId() != null && companiesRepository.existsById(dto.getId())) {
            throw new RuntimeException("Ya existe una empresa con el ID: " + dto.getId());
        }
        Companies company = new Companies();

        company.setName(dto.getName());
        company.setAddress(dto.getAddress());
        company.setNit(dto.getNit());
        company.setEmail(dto.getContactEmail());
        company.setPhone(dto.getContactPhone());

        return companiesRepository.save(company);

    }

    public List<Companies> getAllCompanies() {
        return companiesRepository.findAll();
    }

    public Optional<Companies> getCompanyById(Integer id) {
        return companiesRepository.findById(id);
    }

    public Companies updateCompany(Integer id, CompaniesDTO dto) {
        if (companiesRepository.existsById(id)) {
            Companies company = companiesRepository.findById(id).get();

            // Actualizar solo los campos no nulos del DTO
            if (dto.getName() != null) {
                company.setName(dto.getName());
            }
            if (dto.getAddress() != null) {
                company.setAddress(dto.getAddress());
            }
            if (dto.getNit() != null) {
                company.setNit(dto.getNit());
            }
            if (dto.getContactEmail() != null) {
                company.setEmail(dto.getContactEmail());
            }
            if (dto.getContactPhone() != null) {
                company.setPhone(dto.getContactPhone());
            }
            return companiesRepository.save(company);
        }
        return null;
    }

    public boolean deleteCompany(Integer id) {
        if (companiesRepository.existsById(id)) {
            companiesRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
