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

    // Crear un Board
    public Board addBoard(BoardDTO dto) {
        Board board = new Board();
        board.setBoardId(dto.getId());
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
    public Optional<Board> getBoardById(String id) {
        return boardRepository.findById(id);
    }

    // Actualizar un Board
    public Board updateBoard(Board updatedBoard) {
        if (boardRepository.existsById(updatedBoard.getBoardId())) {
            return boardRepository.save(updatedBoard);
        }
        return null;
    }

    // Eliminar un Board por ID
    public boolean deleteBoard(String id) {
        if (boardRepository.existsById(id)) {
            boardRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
