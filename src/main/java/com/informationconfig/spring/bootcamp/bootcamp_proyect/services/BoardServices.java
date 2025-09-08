package com.informationconfig.spring.bootcamp.bootcamp_proyect.services;
import org.springframework.stereotype.Service;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Board;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.BoardRepository;
import java.util.ArrayList;

@Service
public class BoardServices {
    
    private final BoardRepository boardRepository;

    public BoardServices(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Board addBoard(Board board) {
        return boardRepository.addBoard(board);
    }

    public ArrayList<Board> getAllBoards() {
        return boardRepository.getAllBoards();
    }

    public boolean deleteBoard(String id) {
        return boardRepository.deleteBoard(id);
    }

    public Board updateBoard(String id, Board updatedBoard) {
        return boardRepository.updateBoard(id, updatedBoard);
    }

    public Board getBoardById(String id) {
        return boardRepository.getBoardById(id);
    }

    public ArrayList<Board> getAllBoards(ArrayList<Board> boards) {
        return boardRepository.getAllBoards(boards);
    }
}
