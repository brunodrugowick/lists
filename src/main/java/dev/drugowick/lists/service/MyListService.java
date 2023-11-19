package dev.drugowick.lists.service;

import dev.drugowick.lists.domain.entity.MyList;
import dev.drugowick.lists.domain.repository.MyListRepository;
import org.springframework.stereotype.Service;

@Service
public class MyListService {

    private final MyListRepository listRepository;

    public MyListService(MyListRepository listRepository) {
        this.listRepository = listRepository;
    }

    public MyList add(MyList item) {
        return listRepository.save(item);
    }

    public Iterable<MyList> findAll(String username) {
        return listRepository.findAllByUsername(username);
    }
}
