package com.informationconfig.spring.bootcamp.bootcamp_proyect.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.informationconfig.spring.bootcamp.bootcamp_proyect.dto.ListDTO;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Lists;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.ListsRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ListsService {
    
    private final ListsRepository listsRepository;

    public ListsService(ListsRepository listsRepository) {
        this.listsRepository = listsRepository;
    }

    @Autowired
    private BoardServices boardServices;

    // Solo crear (no actualizar)
    public Lists addList(ListDTO dto) {
        // Validar que la lista no exista
        if (dto.getId() != null && listsRepository.existsById(dto.getId())) {
            throw new RuntimeException("Ya existe una lista con el ID: " + dto.getId());
        }
        
        Lists list = new Lists();
        list.setListId(dto.getId());
        list.setName(dto.getName());
        
        if (dto.getBoardId() != null) {
            boardServices.getBoardById(dto.getBoardId())
                .ifPresent(list::setBoard);
        }
        return listsRepository.save(list);
    }

    // Obtener todas las listas
    public List<Lists> getAllList() {
        return listsRepository.findAll();
    }

    // Obtener un CompanyTutor por ID
    public Optional<Lists> getListById(String id) {
        return listsRepository.findById(id);
    }

    // Actualizar una lista
    public Lists updateList(String id, Lists updatedList) {
        if (listsRepository.existsById(id)) {
            updatedList.setListId(id);
            return listsRepository.save(updatedList);
        }
        return null;
    }

    // Eliminar una lista
    public boolean deleteList(String id) {
        if (listsRepository.existsById(id)) {
            listsRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
