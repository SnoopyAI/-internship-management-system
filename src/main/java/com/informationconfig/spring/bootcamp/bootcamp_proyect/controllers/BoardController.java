package com.informationconfig.spring.bootcamp.bootcamp_proyect.controllers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.informationconfig.spring.bootcamp.bootcamp_proyect.dto.BoardDTO;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Board;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.services.BoardServices;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
            board.getBoardId(),
            board.getName(),
            board.getDescription(),
            board.getStartDate(),
            board.getEndDate(),
            board.getAcademyTutor() != null ? board.getAcademyTutor().getId() : null,
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
            board.getAcademyTutor() != null ? board.getAcademyTutor().getId() : null,
            board.getCompanyTutor() != null ? board.getCompanyTutor().getId() : null
        )).toList();
}

    @GetMapping("/{id}")
    public Optional<BoardDTO> getBoardById(@PathVariable String id) {
        return boardService.getBoardById(id)
            .map(board -> new BoardDTO(
                board.getBoardId(),
                board.getName(),
                board.getDescription(),
                board.getStartDate(),
                board.getEndDate(),
                board.getAcademyTutor() != null ? board.getAcademyTutor().getId() : null,
                board.getCompanyTutor() != null ? board.getCompanyTutor().getId() : null

            ));
    }

    @PatchMapping("/{id}")
    public BoardDTO updateBoard(@PathVariable String id, @Valid @RequestBody BoardDTO dto) {
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
            board.getAcademyTutor() != null ? board.getAcademyTutor().getId() : null,
            board.getCompanyTutor() != null ? board.getCompanyTutor().getId() : null);
    }

    @DeleteMapping("/{id}")
    public boolean deleteBoard(@PathVariable String id) {
        return this.boardService.deleteBoard(id);
    }
}
