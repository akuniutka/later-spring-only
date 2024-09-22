package ru.practicum.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    @Transactional
    public User saveUser(final User user) {
        user.setState(UserState.ACTIVE);
        return repository.save(user);
    }

    @Override
    public User getUser(final long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }
}
