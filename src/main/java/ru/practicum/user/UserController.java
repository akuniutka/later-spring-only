package ru.practicum.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserMapper mapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        log.info("Received GET at /users");
        final List<UserDto> dtos = mapper.mapToDto(userService.getAllUsers());
        log.info("Responded to GTE /users: {}", dtos);
        return dtos;
    }

    @PostMapping
    public UserDto saveNewUser(@RequestBody final NewUserDto newUserDto) {
        log.info("Received POST at /users: {}", newUserDto);
        final User user = mapper.mapToUser(newUserDto);
        final UserDto dto = mapper.mapToDto(userService.saveUser(user));
        log.info("Responded to POST /users: {}", dto);
        return dto;
    }
}
