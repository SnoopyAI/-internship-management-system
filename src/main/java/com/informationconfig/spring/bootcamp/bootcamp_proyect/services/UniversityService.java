package com.informationconfig.spring.bootcamp.bootcamp_proyect.services;

import com.informationconfig.spring.bootcamp.bootcamp_proyect.dto.UniversityDTO;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Universities;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.UniversitiesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UniversityService {
    
    private UniversitiesRepository universitiesRepository;

    public UniversityService(UniversitiesRepository universitiesRepository) {
        this.universitiesRepository = universitiesRepository;
    }

    public Universities addUniversity(UniversityDTO dto) {
        if (dto.getId() != null && universitiesRepository.existsById(dto.getId())) {
            throw new RuntimeException("Ya existe una universidad con el ID: " + dto.getId());
        }

        Universities university = new Universities();
        university.setName(dto.getName());
        
        return universitiesRepository.save(university);

    }

    public List<Universities> getAllUniversities() {
        return universitiesRepository.findAll();
    }

    public Optional<Universities> getUniversityById(Integer id) {
            return universitiesRepository.findById(id);
         
    }

    public Universities updateUniversity(Integer id, UniversityDTO dto) {
        Optional<Universities> opt = universitiesRepository.findById(id);
        if (universitiesRepository.existsById(id)) {
            Universities university = opt.get();
            if (dto.getName() != null) university.setName(dto.getName());
            return universitiesRepository.save(university);
        }

        return null;
    }

    public boolean deleteUniversity(Integer id) {
        if (universitiesRepository.existsById(id)) {
            universitiesRepository.deleteById(id);
            return true;
        }
        return false;
    }
}