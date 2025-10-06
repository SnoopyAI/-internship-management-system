package com.informationconfig.spring.bootcamp.bootcamp_proyect.services;


import org.springframework.stereotype.Service;

import com.informationconfig.spring.bootcamp.bootcamp_proyect.dto.AcademyTutorDTO;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.AcademyTutor;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.AcademyTutorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AcademyTutorService {

    private final AcademyTutorRepository academyTutorRepository;

    public AcademyTutorService(AcademyTutorRepository academyTutorRepository) {
        this.academyTutorRepository = academyTutorRepository;
    }

    // Solo crear (no actualizar)
    public AcademyTutor addAcademyTutor(AcademyTutorDTO dto) {
        // Validar que el tutor no exista
        if (dto.getId() != null && academyTutorRepository.existsById(dto.getId())) {
            throw new RuntimeException("Ya existe un tutor académico con el ID: " + dto.getId());
        }
        
        AcademyTutor academyTutor = new AcademyTutor();
        academyTutor.setId(dto.getId());
        academyTutor.setName(dto.getName());
        academyTutor.setEmail(dto.getEmail());
        academyTutor.setPassword(dto.getPassword());
        academyTutor.setAcademy(dto.getAcademy());
        academyTutor.setDepartment(dto.getDepartment());
        return academyTutorRepository.save(academyTutor);
    }

    // Obtener todos los tutores
    public List<AcademyTutor> getAllAcademyTutors() {
        return academyTutorRepository.findAll();
    }

    // Buscar un tutor por ID
    public Optional<AcademyTutor> getAcademyTutorById(String id) {
        return academyTutorRepository.findById(id);
    }

    // Actualizar un tutor existente
    public AcademyTutor updateAcademyTutor(String id, AcademyTutorDTO dto) {
        // Opcional: validar si existe antes
        if (academyTutorRepository.existsById(id)) {
            AcademyTutor academyTutor = academyTutorRepository.findById(id).get();
            
            // Actualizar solo los campos no nulos del DTO
            if (dto.getName() != null) {
                academyTutor.setName(dto.getName());
            }
            if (dto.getEmail() != null) {
                academyTutor.setEmail(dto.getEmail());
            }
            if (dto.getPassword() != null) {
                academyTutor.setPassword(dto.getPassword());
            }
            if (dto.getAcademy() != null) {
                academyTutor.setAcademy(dto.getAcademy());
            }
            if (dto.getDepartment() != null) {
                academyTutor.setDepartment(dto.getDepartment());
            }
            
            return academyTutorRepository.save(academyTutor);
        }
        return null; // o lanza una excepción
    }

    // Eliminar un tutor por ID
    public boolean deleteAcademyTutor(String id) {
        if (academyTutorRepository.existsById(id)) {
            academyTutorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
