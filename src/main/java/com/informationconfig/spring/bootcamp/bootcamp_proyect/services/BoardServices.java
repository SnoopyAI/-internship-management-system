package com.informationconfig.spring.bootcamp.bootcamp_proyect.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.informationconfig.spring.bootcamp.bootcamp_proyect.dto.BoardDTO;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Board;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.AcademyTutorRepository;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.BoardRepository;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.CompanyTutorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BoardServices {

    private final BoardRepository boardRepository;

    public BoardServices(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }
    
    @Autowired
    private CompanyTutorRepository companyTutorRepository;

    @Autowired
    private AcademyTutorRepository academyTutorRepository;

    // Solo crear (no actualizar)
    public Board addBoard(BoardDTO dto) {
        // Validar que el board no exista
        if (dto.getId() != null && boardRepository.existsById(dto.getId())) {
            throw new RuntimeException("Ya existe un board con el ID: " + dto.getId());
        }
        
        Board board = new Board();
        
        board.setName(dto.getName());
        board.setDescription(dto.getDescription());
        board.setStartDate(dto.getStartDate());
        board.setEndDate(dto.getEndDate());

    if (dto.getAcademyTutorId() != null) {
        academyTutorRepository.findById(dto.getAcademyTutorId())
            .ifPresent(board::setAcademyTutor);
    }

    // Asociar companyTutor si existe
    if (dto.getCompanyTutorId() != null) {
        companyTutorRepository.findById(dto.getCompanyTutorId())
            .ifPresent(board::setCompanyTutor);
    }


        return boardRepository.save(board);
    }

    // Obtener todos los Boards
    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    // Obtener un Board por ID
    public Optional<Board> getBoardById(Integer id) {
        return boardRepository.findById(id);
    }

    // Actualizar un Board
    public Board updateBoard(Integer id, BoardDTO dto) {
        if (boardRepository.existsById(id)) {
            Board board = boardRepository.findById(id).get();
            
            // Actualizar solo los campos no nulos del DTO
            if (dto.getName() != null) {
                board.setName(dto.getName());
            }
            if (dto.getDescription() != null) {
                board.setDescription(dto.getDescription());
            }
            if (dto.getStartDate() != null) {
                board.setStartDate(dto.getStartDate());
            }
            if (dto.getEndDate() != null) {
                board.setEndDate(dto.getEndDate());
            }
            // Note: Tutor relationships should be updated through dedicated endpoints
            
            return boardRepository.save(board);
        }
        return null;
    }

    // Eliminar un Board por ID
    public boolean deleteBoard(Integer id) {
        if (boardRepository.existsById(id)) {
            boardRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
