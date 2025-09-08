package com.informationconfig.spring.bootcamp.bootcamp_proyect.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.LogBook;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.services.LogBookService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/logbooks")
public class LogBookController {
    
    private final LogBookService logBookService;

    public LogBookController(LogBookService logBookService) {
        this.logBookService = logBookService;
    }

    @PostMapping("/add")
    public LogBook add(@Valid @RequestBody LogBook logBook) {
        return this.logBookService.addLogBook(logBook);
    }

    @PostMapping("/createVariable")
    public ArrayList<LogBook> createVariable(@RequestBody ArrayList<LogBook> logBooks) {
        return this.logBookService.getAllLogBooks(logBooks);
    }
    
    @GetMapping("/ReadAll")
    public ArrayList<LogBook> getAllLogBooks() {
        return this.logBookService.getAllLogBooks();
    }

      @GetMapping("/{id}")
    public LogBook getLogBookById(@PathVariable String id) {
        return this.logBookService.getLogBookById(id);
    }

    @PatchMapping("/{id}")
    public LogBook updateLogBook(@PathVariable String id, @Valid @RequestBody LogBook updatedLogBook) {
        return this.logBookService.updateLogBook(id, updatedLogBook);
    }
    
    @DeleteMapping("/{id}")
    public boolean deleteLogBook(@PathVariable String id) {
        return this.logBookService.deleteLogBook(id);
    }
}
