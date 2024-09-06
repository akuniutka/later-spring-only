package ru.practicum.user;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final Map<Long, User> users;
    private long lastUsedId;

    UserRepositoryImpl() {
        this.users = new HashMap<>();
        this.lastUsedId = 0;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User save(final User user) {
        lastUsedId++;
        user.setId(lastUsedId);
        users.put(lastUsedId, user);
        return user;
    }
}
