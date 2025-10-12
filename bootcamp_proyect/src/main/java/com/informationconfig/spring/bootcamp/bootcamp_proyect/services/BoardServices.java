package com.informationconfig.spring.bootcamp.bootcamp_proyect.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.informationconfig.spring.bootcamp.bootcamp_proyect.dto.BoardDTO;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Board;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.AcademyTutor;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.CompanyTutor;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Intern;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.BoardRepository;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.AcademyTutorRepository;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.CompanyTutorRepository;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.InternRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BoardServices {

    private final BoardRepository boardRepository;

    @Autowired
    private CompanyTutorRepository companyTutorRepository;
    
    @Autowired
    private AcademyTutorRepository academyTutorRepository;
    
    @Autowired
    private InternRepository internRepository;

    public BoardServices(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

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

    if (dto.getId() != null) {
        companyTutorRepository.findById(dto.getId())
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
    
    // Añadir un AcademyTutor al Board
    @Transactional
    public Board addAcademicTutorToBoard(Integer boardId, Integer tutorId) {
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new RuntimeException("Board no encontrado con ID: " + boardId));
        
        AcademyTutor tutor = academyTutorRepository.findById(tutorId)
            .orElseThrow(() -> new RuntimeException("Tutor Académico no encontrado con ID: " + tutorId));
        
        // Verificar si el tutor ya está asignado a este board
        if (tutor.getBoard() != null && tutor.getBoard().getBoardId().equals(boardId)) {
            return board; // Ya está asignado
        }
        
        // Asignar el board al tutor (la relación está en AcademyTutor)
        tutor.setBoard(board);
        academyTutorRepository.save(tutor);
        
        // Recargar el board para obtener la lista actualizada
        return boardRepository.findById(boardId).orElse(board);
    }
    
    // Asignar CompanyTutor al Board (solo uno)
    public Board setCompanyTutor(Integer boardId, Integer tutorId) {
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new RuntimeException("Board no encontrado con ID: " + boardId));
        
        CompanyTutor tutor = companyTutorRepository.findById(tutorId)
            .orElseThrow(() -> new RuntimeException("Tutor de Empresa no encontrado con ID: " + tutorId));
        
        board.setCompanyTutor(tutor);
        return boardRepository.save(board);
    }
    
    // Eliminar AcademyTutor del Board
    @Transactional
    public Board removeAcademicTutorFromBoard(Integer boardId, Integer tutorId) {
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new RuntimeException("Board no encontrado con ID: " + boardId));
        
        AcademyTutor tutor = academyTutorRepository.findById(tutorId)
            .orElseThrow(() -> new RuntimeException("Tutor Académico no encontrado con ID: " + tutorId));
        
        // Verificar que el tutor pertenece a este board
        if (tutor.getBoard() != null && tutor.getBoard().getBoardId().equals(boardId)) {
            tutor.setBoard(null);
            academyTutorRepository.save(tutor);
        }
        
        // Recargar el board para obtener la lista actualizada
        return boardRepository.findById(boardId).orElse(board);
    }
    
    // Eliminar CompanyTutor del Board
    @Transactional
    public Board removeCompanyTutor(Integer boardId) {
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new RuntimeException("Board no encontrado con ID: " + boardId));
        
        board.setCompanyTutor(null);
        return boardRepository.save(board);
    }
    
    // Eliminar Intern del Board
    @Transactional
    public Board removeInternFromBoard(Integer boardId, Integer internId) {
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new RuntimeException("Board no encontrado con ID: " + boardId));
        
        Intern intern = internRepository.findById(internId)
            .orElseThrow(() -> new RuntimeException("Interno no encontrado con ID: " + internId));
        
        // Verificar que el interno pertenece a este board
        if (intern.getBoard() != null && intern.getBoard().getBoardId().equals(boardId)) {
            intern.setBoard(null);
            internRepository.save(intern);
        }
        
        // Recargar el board para obtener la lista actualizada
        return boardRepository.findById(boardId).orElse(board);
    }
}
