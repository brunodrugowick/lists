package dev.drugowick.lists.service;

import dev.drugowick.lists.domain.entity.MyList;
import dev.drugowick.lists.domain.repository.MyListRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class MyListService {

    private final MyListRepository repository;

    public MyListService(MyListRepository repository) {
        this.repository = repository;
    }

    public MyList add(MyList item) {
        return repository.save(item);
    }

    public Iterable<MyList> findAll(String username) {
        return repository.findAllByUsername(username);
    }

    public MyList findByUUID(UUID uuid, String name) {
        return repository.findByIdAndUsername(uuid, name).orElseThrow();
    }

    @Transactional
    public void deleteItem(UUID listId, UUID itemId, String username) {
        var listOptional = this.repository.findOneByIdAndUsername(listId, username);
        listOptional.ifPresent(myList -> {
            myList.removeItemByUUID(itemId);
            this.repository.save(myList);
        });
    }
}
