package ru.practicum.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.List;

@Mapper
public interface UserMapper {

    @Mapping(target = "registrationDate", expression = "java(now())")
    @Mapping(target = "state", expression = "java(UserState.ACTIVE)")
    User mapToUser(NewUserDto dto);

    UserDto mapToDto(User user);

    List<UserDto> mapToDto(List<User> users);

    default Instant now() {
        return Instant.now();
    }
}
