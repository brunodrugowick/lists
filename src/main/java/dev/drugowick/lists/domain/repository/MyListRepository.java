package dev.drugowick.lists.domain.repository;

import dev.drugowick.lists.domain.entity.MyList;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;

@org.springframework.stereotype.Repository
@SQLDelete(sql = "UPDATE lists SET active = false WHERE id=?")
@Where(clause = "active=true")
public interface MyListRepository extends Repository<MyList, UUID> {

    MyList save(MyList list);

    Optional<MyList> findByIdAndUsername(UUID id, String username);
    Iterable<MyList> findAllByUsername(String username);
    Optional<MyList> findOneByIdAndUsername(UUID id, String username);
}
