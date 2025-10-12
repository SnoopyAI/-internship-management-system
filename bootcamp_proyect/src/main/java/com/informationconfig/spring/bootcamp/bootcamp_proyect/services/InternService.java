package com.informationconfig.spring.bootcamp.bootcamp_proyect.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.informationconfig.spring.bootcamp.bootcamp_proyect.dto.InternDTO;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Board;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Intern;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Universities;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.AcademyTutorRepository;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.BoardRepository;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.CompanyTutorRepository;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.InternRepository;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.UniversitiesRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InternService {
    private final InternRepository internRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CompanyTutorRepository companyTutorRepository;

    @Autowired
    private AcademyTutorRepository academyTutorRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UniversitiesRepository universitiesRepository;

    public InternService(InternRepository internRepository) {
        this.internRepository = internRepository;
    }

    public Intern addIntern(InternDTO dto) {
        // Validar que el interno no exista
        if (dto.getId() != null && internRepository.existsById(dto.getId())) {
            throw new RuntimeException("Ya existe un interno con el ID: " + dto.getId());
        }
        
        Intern intern = new Intern();
        intern.setName(dto.getName());
        intern.setEmail(dto.getEmail());
        intern.setPassword(passwordEncoder.encode(dto.getPassword()));
        intern.setCareer(dto.getCareer());
        intern.setSemester(dto.getSemester());
        
        // Asociar board (opcional en registro inicial)
        if (dto.getBoardId() != null) {
            Board board = boardRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new RuntimeException("Board con ID " + dto.getBoardId() + " no encontrado"));
            intern.setBoard(board);

            // Asociar academyTutor - puede venir del DTO o heredarse del board
            if (dto.getAcademyTutorId() != null) {
                intern.setAcademyTutor(
                    academyTutorRepository.findById(dto.getAcademyTutorId())
                        .orElseThrow(() -> new RuntimeException("Tutor académico con ID " + dto.getAcademyTutorId() + " no encontrado"))
                );
            } else if (board.getAcademyTutors() != null && !board.getAcademyTutors().isEmpty()) {
                // Si no viene en el DTO, intentar tomar el primer tutor académico del board
                intern.setAcademyTutor(board.getAcademyTutors().get(0));
            }

            // Asociar companyTutor - puede venir del DTO o heredarse del board
            if (dto.getCompanyTutorId() != null) {
                intern.setCompanyTutor(
                    companyTutorRepository.findById(dto.getCompanyTutorId())
                        .orElseThrow(() -> new RuntimeException("Tutor de empresa con ID " + dto.getCompanyTutorId() + " no encontrado"))
                );
            } else if (board.getCompanyTutor() != null) {
                // Si no viene en el DTO, tomar el tutor de empresa del board
                intern.setCompanyTutor(board.getCompanyTutor());
            }
        } else {
            // Si no hay boardId, permitir tutores directos del DTO
            if (dto.getAcademyTutorId() != null) {
                intern.setAcademyTutor(
                    academyTutorRepository.findById(dto.getAcademyTutorId())
                        .orElseThrow(() -> new RuntimeException("Tutor académico con ID " + dto.getAcademyTutorId() + " no encontrado"))
                );
            }
            if (dto.getCompanyTutorId() != null) {
                intern.setCompanyTutor(
                    companyTutorRepository.findById(dto.getCompanyTutorId())
                        .orElseThrow(() -> new RuntimeException("Tutor de empresa con ID " + dto.getCompanyTutorId() + " no encontrado"))
                );
            }
        }

        // Asociar university (opcional en registro inicial)
        if (dto.getUniversity() != null) {
            Universities university = universitiesRepository.findById(dto.getUniversity())
                .orElseThrow(() -> new RuntimeException("Universidad con ID " + dto.getUniversity() + " no encontrada"));
            intern.setUniversity(university);
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
                intern.setPassword(passwordEncoder.encode(dto.getPassword()));
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
