package com.informationconfig.spring.bootcamp.bootcamp_proyect.services;
import org.springframework.stereotype.Service;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.LogBook;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.LogBookRepository;
import java.util.ArrayList;

@Service
public class LogBookService {
    
    private final LogBookRepository logBookRepository;

    public LogBookService(LogBookRepository logBookRepository) {
        this.logBookRepository = logBookRepository;
    }

    public LogBook addLogBook(LogBook logBook) {
        return logBookRepository.addLogBook(logBook);
    }

    public ArrayList<LogBook> getAllLogBooks() {
        return logBookRepository.getAllLogBooks();
    }

    public boolean deleteLogBook(String id) {
        return logBookRepository.deleteLogBook(id);
    }

    public LogBook updateLogBook(String id, LogBook updatedLogBook) {
        return logBookRepository.updateLogBook(id, updatedLogBook);
    }

    public LogBook getLogBookById(String id) {
        return logBookRepository.getLogBookById(id);
    }

    public ArrayList<LogBook> getAllLogBooks(ArrayList<LogBook> logBooks) {
        return logBookRepository.getAllLogBooks(logBooks);
    }
}
