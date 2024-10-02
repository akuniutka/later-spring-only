package ru.practicum.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.config.AppConfig;
import ru.practicum.config.PersistenceConfig;

import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;

@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestPropertySource(properties = {
        "jdbc.url=jdbc:h2:mem:later;INIT=RUNSCRIPT FROM './src/test/resources/h2init.sql';",
        "jdbc.driverClassName=org.h2.Driver"})
@SpringJUnitConfig({AppConfig.class, PersistenceConfig.class, UserServiceImpl.class})
class UserServiceImplIT {

    private final EntityManager em;
    private final UserService service;

    @Test
    void testSaveUser() {
        final User user = makeUser("John", "Doe", "jdoe@google.com");

        service.saveUser(user);

        final TypedQuery<User> query = em.createQuery("select u from User u where u.email = :email", User.class);
        final User savedUser = query
                .setParameter("email", user.getEmail())
                .getSingleResult();

        assertThat(savedUser.getId(), notNullValue());
        assertThat(savedUser.getFirstName(), equalTo(user.getFirstName()));
        assertThat(savedUser.getLastName(), equalTo(user.getLastName()));
        assertThat(savedUser.getEmail(), equalTo(user.getEmail()));
        assertThat(savedUser.getState(), equalTo(UserState.ACTIVE));
        assertThat(savedUser.getRegistrationDate(), notNullValue());
    }

    @Test
    void testGetAllUsers() {
        final List<User> users = List.of(
                makeUser("John", "Doe", "jdoe@google.com"),
                makeUser("Adrian", "Paul", "paul@aol.com"),
                makeUser("Luke", "Skywalker", "jedi@yahoo.com")
        );
        users.forEach(service::saveUser);

        final List<User> savedUsers = service.getAllUsers();

        assertThat(savedUsers, hasSize(users.size()));
        assertThat(savedUsers, hasItems(
                allOf(
                        hasProperty("id", notNullValue()),
                        hasProperty("firstName", equalTo("John")),
                        hasProperty("lastName", equalTo("Doe")),
                        hasProperty("email", equalTo("jdoe@google.com")),
                        hasProperty("state", equalTo(UserState.ACTIVE)),
                        hasProperty("registrationDate", notNullValue())),
                allOf(
                        hasProperty("id", notNullValue()),
                        hasProperty("firstName", equalTo("Adrian")),
                        hasProperty("lastName", equalTo("Paul")),
                        hasProperty("email", equalTo("paul@aol.com")),
                        hasProperty("state", equalTo(UserState.ACTIVE)),
                        hasProperty("registrationDate", notNullValue())),
                allOf(
                        hasProperty("id", notNullValue()),
                        hasProperty("firstName", equalTo("Luke")),
                        hasProperty("lastName", equalTo("Skywalker")),
                        hasProperty("email", equalTo("jedi@yahoo.com")),
                        hasProperty("state", equalTo(UserState.ACTIVE)),
                        hasProperty("registrationDate", notNullValue()))
        ));
    }

    private User makeUser(final String firstName, final String lastName, final String email) {
        final User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        return user;
    }
}