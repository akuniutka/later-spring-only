package ru.practicum.user;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    User getUser(long id);

    List<User> getAllUsers();
}
