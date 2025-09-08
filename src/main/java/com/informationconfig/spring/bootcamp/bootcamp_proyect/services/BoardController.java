package com.informationconfig.spring.bootcamp.bootcamp_proyect.services;
import org.springframework.stereotype.Service;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Board;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.BoardRepository;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Service
@RequestMapping("/boards")
public class BoardController {
    
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/add")
    public Board add(@RequestBody Board board) {
        return this.boardService.addBoard(board);
    }

    @PostMapping("/createVariable")
    public ArrayList<Board> createVariable(@RequestBody ArrayList<Board> boards) {
        return this.boardService.getAllBoards(boards);
    }
    
    @GetMapping("/ReadAll")
    public ArrayList<Board> getAllBoards() {
        return this.boardService.getAllBoards();
    }

      @GetMapping("/{id}")
    public Board getBoardById(@PathVariable String id) {
        return this.boardService.getBoardById(id);
    }

    @PatchMapping("/{id}")
    public Board updateBoard(@PathVariable String id, @RequestBody Board updatedBoard) {
        return this.boardService.updateBoard(id, updatedBoard);
    }
    
    @DeleteMapping("/{id}")
    public boolean deleteBoard(@PathVariable String id) {
        return this.boardService.deleteBoard(id);
    }
}
