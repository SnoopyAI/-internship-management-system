package com.informationconfig.spring.bootcamp.bootcamp_proyect.controllers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Lists;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.services.ListsService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

import com.informationconfig.spring.bootcamp.bootcamp_proyect.dto.ListDTO;
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
    public ListDTO add(@Valid @RequestBody ListDTO dto) {
        Lists lists = listsService.addList(dto);
        return new ListDTO(

            lists.getName(),
            lists.getBoard() != null ? lists.getBoard().getBoardId() : null
        );
    }

    @PostMapping("/createVariable")
    public List<Lists> createVariable(@RequestBody List<Lists> listss) {
        return this.listsService.getAllList();
    }
    
    @GetMapping("/ReadAll")
    public List<ListDTO> getAllListss() {
        List<Lists> lists = this.listsService.getAllList();
        return lists.stream().map(list -> new ListDTO(

            list.getListId(),
            list.getName(),
            list.getBoard() != null ? list.getBoard().getBoardId() : null
        )).toList();
    }

    @GetMapping("/{id}")
    public Optional<ListDTO> getListsById(@PathVariable Integer id) {
        return this.listsService.getListById(id)
            .map(list -> new ListDTO(
                
                list.getName(),
                list.getBoard() != null ? list.getBoard().getBoardId() : null
            ));
    }

    @PatchMapping("/{id}")
    public ListDTO updateLists(@PathVariable Integer id, @Valid @RequestBody ListDTO dto) {
        Lists existing = this.listsService.getListById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        
        Lists updated = this.listsService.updateList(id, dto);
        return new ListDTO(
            
            updated.getListId(),
            updated.getName(),
            updated.getBoard() != null ? updated.getBoard().getBoardId() : null
        );
    }
    
    @DeleteMapping("/{id}")
    public boolean deleteLists(@PathVariable Integer id) {
        return this.listsService.deleteList(id);
    }
}
