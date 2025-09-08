package com.informationconfig.spring.bootcamp.bootcamp_proyect.repository;
import org.springframework.stereotype.Repository;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.LogBook;
import java.util.ArrayList;

@Repository
public class LogBookRepository {
    
    private ArrayList<LogBook> logBooks = new ArrayList<>();

    public LogBook addLogBook(LogBook logBook) {
        logBooks.add(logBook);
        return logBook;
    }

    public ArrayList<LogBook> getAllLogBooks(ArrayList<LogBook> logBooks) {
        logBooks.addAll(logBooks);
        return logBooks;
    }

    public boolean deleteLogBook(String id) {
        for (int i = 0; i < logBooks.size(); i++) {
            if (logBooks.get(i).getLogBookId() == id) {
                logBooks.remove(i);
                return true;
            }
        }
        return false;
    }

    public LogBook updateLogBook(String id, LogBook updatedLogBook) {
        for (int i = 0; i < logBooks.size(); i++) {
            if (logBooks.get(i).getLogBookId().equals(id)) {
                logBooks.set(i, updatedLogBook);
                return updatedLogBook;
            }
        }
        return null; // or throw an exception if preferred
    }

    public LogBook getLogBookById(String id) {
        for (LogBook logBook : logBooks) {
            if (logBook.getLogBookId().equals(id)) {
                return logBook;
            }
        }
        return null; // or throw an exception if preferred
    }

    public ArrayList<LogBook> getAllLogBooks() {
        return logBooks;
    }
}
