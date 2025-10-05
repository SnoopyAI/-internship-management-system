package com.informationconfig.spring.bootcamp.bootcamp_proyect.services;
import org.springframework.stereotype.Service;
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

    // Crear una nueva lista
    public Lists addList(Lists list) {
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
