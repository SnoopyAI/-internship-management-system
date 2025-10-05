package com.informationconfig.spring.bootcamp.bootcamp_proyect.controllers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Lists;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.services.ListsService;
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
    public List<Lists> createVariable(@RequestBody List<Lists> listss) {
        return this.listsService.getAllList();
    }
    
    @GetMapping("/ReadAll")
    public List<Lists> getAllListss() {
        return this.listsService.getAllList();
    }

      @GetMapping("/{id}")
    public Optional<Lists> getListsById(@PathVariable String id) {
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
