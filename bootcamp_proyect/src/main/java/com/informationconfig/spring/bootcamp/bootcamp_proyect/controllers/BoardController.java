package com.informationconfig.spring.bootcamp.bootcamp_proyect.controllers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.informationconfig.spring.bootcamp.bootcamp_proyect.dto.BoardDTO;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Board;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.services.BoardServices;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/boards")
public class BoardController {
    
    private final BoardServices boardService;

    public BoardController(BoardServices boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/add")
    public BoardDTO add(@RequestBody BoardDTO dto) {
    Board board = boardService.addBoard(dto);
        return new BoardDTO(
            
            board.getName(),
            board.getDescription(),
            board.getStartDate(),
            board.getEndDate(),
            board.getCompanyTutor() != null ? board.getCompanyTutor().getId() : null);
    }


    @PostMapping("/createVariable")
    public List<Board> createVariable(@RequestBody List<Board> boards) {
        return this.boardService.getAllBoards();
    }

    @GetMapping("/ReadAll")
    public List<BoardDTO> getAllBoards() {
    List<Board> boards = boardService.getAllBoards();
        return boards.stream().map(board -> new BoardDTO(

            board.getBoardId(),
            board.getName(),
            board.getDescription(),
            board.getStartDate(),
            board.getEndDate(),
            board.getCompanyTutor() != null ? board.getCompanyTutor().getId() : null
        )).toList();
}

    @GetMapping("/{id}")
    public Optional<BoardDTO> getBoardById(@PathVariable Integer id) {
        return boardService.getBoardById(id)
            .map(board -> new BoardDTO(
                board.getBoardId(),
                board.getName(),
                board.getDescription(),
                board.getStartDate(),
                board.getEndDate(),
                board.getCompanyTutor() != null ? board.getCompanyTutor().getId() : null

            ));
    }
    
    @GetMapping("/{id}/details")
    public Map<String, Object> getBoardDetails(@PathVariable Integer id) {
        Board board = boardService.getBoardById(id)
            .orElseThrow(() -> new RuntimeException("Board no encontrado"));
        
        Map<String, Object> details = new HashMap<>();
        details.put("id", board.getBoardId());
        details.put("name", board.getName());
        details.put("description", board.getDescription());
        details.put("startDate", board.getStartDate());
        details.put("endDate", board.getEndDate());
        
        // Añadir tutores académicos
        if (board.getAcademyTutors() != null) {
            details.put("academyTutors", board.getAcademyTutors().stream()
                .map(tutor -> {
                    Map<String, Object> tutorMap = new HashMap<>();
                    tutorMap.put("id", tutor.getId());
                    tutorMap.put("name", tutor.getName());
                    tutorMap.put("email", tutor.getEmail());
                    tutorMap.put("department", tutor.getDepartment());
                    return tutorMap;
                })
                .collect(Collectors.toList()));
        }
        
        // Añadir tutor de empresa
        if (board.getCompanyTutor() != null) {
            Map<String, Object> companyTutorMap = new HashMap<>();
            companyTutorMap.put("id", board.getCompanyTutor().getId());
            companyTutorMap.put("name", board.getCompanyTutor().getName());
            companyTutorMap.put("email", board.getCompanyTutor().getEmail());
            details.put("companyTutor", companyTutorMap);
        }
        
        return details;
    }

    @PatchMapping("/{id}")
    public BoardDTO updateBoard(@PathVariable Integer id, @Valid @RequestBody BoardDTO dto) {
        Board existing = this.boardService.getBoardById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        
        Board board = this.boardService.updateBoard(id, dto);
        return new BoardDTO(
            board.getBoardId(),
            board.getName(),
            board.getDescription(),
            board.getStartDate(),
            board.getEndDate(),
            board.getCompanyTutor() != null ? board.getCompanyTutor().getId() : null);
    }
    
    @PutMapping("/{id}")
    public BoardDTO updateBoardPut(@PathVariable Integer id, @Valid @RequestBody BoardDTO dto) {
        Board existing = this.boardService.getBoardById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        
        Board board = this.boardService.updateBoard(id, dto);
        return new BoardDTO(
            board.getBoardId(),
            board.getName(),
            board.getDescription(),
            board.getStartDate(),
            board.getEndDate(),
            board.getCompanyTutor() != null ? board.getCompanyTutor().getId() : null);
    }

    @DeleteMapping("/{id}")
    public boolean deleteBoard(@PathVariable Integer id) {
        return this.boardService.deleteBoard(id);
    }
    
    // Añadir un AcademyTutor al Board
    @PutMapping("/{boardId}/academicTutor/{tutorId}")
    public BoardDTO addAcademicTutor(@PathVariable Integer boardId, @PathVariable Integer tutorId) {
        Board board = boardService.addAcademicTutorToBoard(boardId, tutorId);
        return new BoardDTO(
            board.getBoardId(),
            board.getName(),
            board.getDescription(),
            board.getStartDate(),
            board.getEndDate(),
            board.getCompanyTutor() != null ? board.getCompanyTutor().getId() : null
        );
    }
    
    // Asignar CompanyTutor al Board
    @PutMapping("/{boardId}/companyTutor/{tutorId}")
    public BoardDTO setCompanyTutor(@PathVariable Integer boardId, @PathVariable Integer tutorId) {
        Board board = boardService.setCompanyTutor(boardId, tutorId);
        return new BoardDTO(
            board.getBoardId(),
            board.getName(),
            board.getDescription(),
            board.getStartDate(),
            board.getEndDate(),
            board.getCompanyTutor() != null ? board.getCompanyTutor().getId() : null
        );
    }
    
    // Eliminar AcademyTutor del Board
    @DeleteMapping("/{boardId}/academicTutor/{tutorId}")
    public BoardDTO removeAcademicTutorFromBoard(@PathVariable Integer boardId, @PathVariable Integer tutorId) {
        Board board = boardService.removeAcademicTutorFromBoard(boardId, tutorId);
        return new BoardDTO(
            board.getBoardId(),
            board.getName(),
            board.getDescription(),
            board.getStartDate(),
            board.getEndDate(),
            board.getCompanyTutor() != null ? board.getCompanyTutor().getId() : null
        );
    }
    
    // Eliminar CompanyTutor del Board
    @DeleteMapping("/{boardId}/companyTutor")
    public BoardDTO removeCompanyTutor(@PathVariable Integer boardId) {
        Board board = boardService.removeCompanyTutor(boardId);
        return new BoardDTO(
            board.getBoardId(),
            board.getName(),
            board.getDescription(),
            board.getStartDate(),
            board.getEndDate(),
            board.getCompanyTutor() != null ? board.getCompanyTutor().getId() : null
        );
    }
    
    // Eliminar Intern del Board
    @DeleteMapping("/{boardId}/intern/{internId}")
    public BoardDTO removeInternFromBoard(@PathVariable Integer boardId, @PathVariable Integer internId) {
        Board board = boardService.removeInternFromBoard(boardId, internId);
        return new BoardDTO(
            board.getBoardId(),
            board.getName(),
            board.getDescription(),
            board.getStartDate(),
            board.getEndDate(),
            board.getCompanyTutor() != null ? board.getCompanyTutor().getId() : null
        );
    }
}
