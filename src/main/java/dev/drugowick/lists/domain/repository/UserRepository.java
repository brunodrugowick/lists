package dev.drugowick.lists.domain.repository;

import dev.drugowick.lists.domain.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findByProviderAndProviderId(String provider, String providerId);

}
