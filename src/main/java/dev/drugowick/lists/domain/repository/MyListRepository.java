package dev.drugowick.lists.domain.repository;

import dev.drugowick.lists.domain.entity.MyList;
import org.springframework.data.repository.Repository;

import java.util.UUID;

@org.springframework.stereotype.Repository
public interface MyListRepository extends Repository<MyList, UUID> {

    Iterable<MyList> findAllByUsername(String username);
    MyList save(MyList list);
}
