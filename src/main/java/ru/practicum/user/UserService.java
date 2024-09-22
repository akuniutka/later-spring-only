package ru.practicum.user;

import java.util.List;

public interface UserService {

    User getUser(long id);

    List<User> getAllUsers();

    User saveUser(User user);
}
