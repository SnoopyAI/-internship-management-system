package com.informationconfig.spring.bootcamp.bootcamp_proyect.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.informationconfig.spring.bootcamp.bootcamp_proyect.dto.InternDTO;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Intern;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.CompanyTutorRepository;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.AcademyTutorRepository;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.BoardRepository;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.InternRepository;
import java.util.List;
import java.util.Optional;


@Service
public class InternService {
    
    private final InternRepository internRepository;

    public InternService(InternRepository internRepository) {
        this.internRepository = internRepository;
    }

    @Autowired
    private CompanyTutorRepository companyTutorRepository;

    @Autowired
    private AcademyTutorRepository academyTutorRepository;

    @Autowired
    private BoardRepository boardRepository;

    public Intern addIntern(InternDTO dto) {
        // Validar que el interno no exista
        if (dto.getId() != null && internRepository.existsById(dto.getId())) {
            throw new RuntimeException("Ya existe un interno con el ID: " + dto.getId());
        }
        
        Intern intern = new Intern();
        intern.setId(dto.getId());
        intern.setName(dto.getName());
        intern.setEmail(dto.getEmail());
        intern.setPassword(dto.getPassword());
        intern.setUniversity(dto.getUniversity());
        intern.setCareer(dto.getCareer());
        intern.setSemester(dto.getSemester());
        

    // Asociar academyTutor si existe
    if (dto.getAcademyTutorId() != null) {
        academyTutorRepository.findById(dto.getAcademyTutorId())
            .ifPresent(intern::setAcademyTutor);
    }

    // Asociar companyTutor si existe
    if (dto.getCompanyTutorId() != null) {
        companyTutorRepository.findById(dto.getCompanyTutorId())
            .ifPresent(intern::setCompanyTutor);
    }

    if (dto.getBoardId() != null) {
        boardRepository.findById(dto.getBoardId())
            .ifPresent(intern::setBoard);
    }

    return internRepository.save(intern);
}

    

    // Botener todos los Internos
    public List<Intern> getAllInterns() {
        return internRepository.findAll();
    }

    // Obtener Interno por ID
    public Optional<Intern> getInternById(Integer id) {
        return internRepository.findById(id);
    }

    // Actualizar Interno
     public Intern updateIntern(Integer id, InternDTO dto) {
        if (internRepository.existsById(id)) {
            Intern intern = internRepository.findById(id).get();
            
            // Actualizar solo los campos no nulos del DTO
            if (dto.getName() != null) {
                intern.setName(dto.getName());
            }
            if (dto.getEmail() != null) {
                intern.setEmail(dto.getEmail());
            }
            if (dto.getPassword() != null) {
                intern.setPassword(dto.getPassword());
            }
            if (dto.getUniversity() != null) {
                intern.setUniversity(dto.getUniversity());
            }
            if (dto.getCareer() != null) {
                intern.setCareer(dto.getCareer());
            }
            if (dto.getSemester() != 0) {
                intern.setSemester(dto.getSemester());
            }
            // Note: Tutor relationships should be updated through dedicated endpoints
            
            return internRepository.save(intern);
        }
        return null;
    }

    // Eliminar un Interno
    public boolean deleteIntern(Integer id) {
        if (internRepository.existsById(id)) {
            internRepository.deleteById(id);
            return true;
        }
        return false;
    }

   
}
