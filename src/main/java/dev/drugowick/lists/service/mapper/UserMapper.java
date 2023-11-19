package dev.drugowick.lists.service.mapper;

import dev.drugowick.lists.domain.entity.User;
import dev.drugowick.lists.service.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDto(User user);

    User toEntity(UserDTO userDto);

}
