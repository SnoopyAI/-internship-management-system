package com.informationconfig.spring.bootcamp.bootcamp_proyect.services;
import org.springframework.stereotype.Service;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Lists;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.ListsRepository;
import java.util.ArrayList;

@Service
public class ListsService {
    
    private final ListsRepository listsRepository;

    public ListsService(ListsRepository listsRepository) {
        this.listsRepository = listsRepository;
    }

    public Lists addList(Lists list) {
        return listsRepository.addList(list);
    }

    public ArrayList<Lists> getAllLists() {
        return listsRepository.getAllLists();
    }

    public boolean deleteList(String id) {
        return listsRepository.deleteList(id);
    }

    public Lists updateList(String id, Lists updatedList) {
        return listsRepository.updateList(id, updatedList);
    }

    public Lists getListById(String id) {
        return listsRepository.getListById(id);
    }

    public ArrayList<Lists> getAllLists(ArrayList<Lists> lists) {
        return listsRepository.getAllLists(lists);
    }
}
