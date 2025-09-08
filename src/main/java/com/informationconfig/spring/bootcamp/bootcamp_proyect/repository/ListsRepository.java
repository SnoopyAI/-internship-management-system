package com.informationconfig.spring.bootcamp.bootcamp_proyect.repository;
import org.springframework.stereotype.Repository;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Lists;
import java.util.ArrayList;

@Repository
public class ListsRepository {
    
    private ArrayList<Lists> lists = new ArrayList<>();

    public Lists addList(Lists list) {
        lists.add(list);
        return list;
    }

    public ArrayList<Lists> getAllLists(ArrayList<Lists> lists) {
        lists.addAll(lists);
        return lists;
    }

    public boolean deleteList(String id) {
        for (int i = 0; i < lists.size(); i++) {
            if (lists.get(i).getListId() == id) {
                lists.remove(i);
                return true;
            }
        }
        return false;
    }

    public Lists updateList(String id, Lists updatedList) {
        for (int i = 0; i < lists.size(); i++) {
            if (lists.get(i).getListId().equals(id)) {
                lists.set(i, updatedList);
                return updatedList;
            }
        }
        return null; // or throw an exception if preferred
    }

    public Lists getListById(String id) {
        for (Lists list : lists) {
            if (list.getListId().equals(id)) {
                return list;
            }
        }
        return null; // or throw an exception if preferred
    }

    public ArrayList<Lists> getAllLists() {
        return lists;
    }
}
