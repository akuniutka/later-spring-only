package ru.practicum.user;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    User mapToUser(NewUserDto dto);

    UserDto mapToDto(User user);

    List<UserDto> mapToDto(List<User> users);
}
