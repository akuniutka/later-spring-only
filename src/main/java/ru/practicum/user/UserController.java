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

    @GetMapping
    public List<User> getAllUsers() {
        log.info("Received GET at /users");
        final List<User> users = userService.getAllUsers();
        log.info("Responded to GTE /users");
        return users;
    }

    @PostMapping
    public User saveNewUser(@RequestBody final User user) {
        log.info("Received POST at /users: {}", user);
        final User savedUser = userService.saveUser(user);
        log.info("Responded to POST /users: {}", savedUser);
        return savedUser;
    }
}
