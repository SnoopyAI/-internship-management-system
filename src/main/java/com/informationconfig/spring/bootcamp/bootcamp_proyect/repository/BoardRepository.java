package com.informationconfig.spring.bootcamp.bootcamp_proyect.repository;
import org.springframework.stereotype.Repository;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Board;
import java.util.ArrayList;

@Repository
public class BoardRepository {
    
    private ArrayList<Board> boards = new ArrayList<>();

    public Board addBoard(Board board) {
        boards.add(board);
        return board;
    }

    public ArrayList<Board> getAllBoards(ArrayList<Board> boards) {
        boards.addAll(boards);
        return boards;
    }

    public boolean deleteBoard(String id) {
        for (int i = 0; i < boards.size(); i++) {
            if (boards.get(i).getBoardId() == id) {
                boards.remove(i);
                return true;
            }
        }
        return false;
    }

    public Board updateBoard(String id, Board updatedBoard) {
        for (int i = 0; i < boards.size(); i++) {
            if (boards.get(i).getBoardId().equals(id)) {
                boards.set(i, updatedBoard);
                return updatedBoard;
            }
        }
        return null; // or throw an exception if preferred
    }

    public Board getBoardById(String id) {
        for (Board board : boards) {
            if (board.getBoardId().equals(id)) {
                return board;
            }
        }
        return null; // or throw an exception if preferred
    }

    public ArrayList<Board> getAllBoards() {
        return boards;
    }
}
