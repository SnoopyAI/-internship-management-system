package com.informationconfig.spring.bootcamp.bootcamp_proyect.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Lists;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.services.ListsService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/lists")
public class ListsController {
    
    private final ListsService listsService;

    public ListsController(ListsService listsService) {
        this.listsService = listsService;
    }

    @PostMapping("/add")
    public Lists add(@Valid @RequestBody Lists lists) {
        return this.listsService.addList(lists);
    }

    @PostMapping("/createVariable")
    public ArrayList<Lists> createVariable(@RequestBody ArrayList<Lists> listss) {
        return this.listsService.getAllLists(listss);
    }
    
    @GetMapping("/ReadAll")
    public ArrayList<Lists> getAllListss() {
        return this.listsService.getAllLists();
    }

      @GetMapping("/{id}")
    public Lists getListsById(@PathVariable String id) {
        return this.listsService.getListById(id);
    }

    @PatchMapping("/{id}")
    public Lists updateLists(@PathVariable String id, @Valid @RequestBody Lists updatedLists) {
        return this.listsService.updateList(id, updatedLists);
    }
    
    @DeleteMapping("/{id}")
    public boolean deleteLists(@PathVariable String id) {
        return this.listsService.deleteList(id);
    }
}
