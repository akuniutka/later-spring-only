package ru.practicum.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.common.ControllerExceptionHandler;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerIT {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private UserService userService;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserController controller;
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(ControllerExceptionHandler.class)
                .build();
        reset(userService, userMapper);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(userService, userMapper);
    }

    @Test
    void testSaveNewUser() throws Exception {
        final NewUserDto dto = makeNewUserDto("John", "Doe", "jdoe@google.com");
        final Instant now = Instant.ofEpochMilli(1596978600000L);
        final String expectedJson = "{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\","
                + "\"email\":\"jdoe@google.com\",\"state\":\"ACTIVE\",\"registrationDate\":\"2020-08-09, 13:10:00\"}";

        when(userMapper.mapToUser(dto))
                .thenReturn(makeUser(dto, null, UserState.ACTIVE, now));
        when(userService.saveUser(makeUser(dto, null, UserState.ACTIVE, now)))
                .thenReturn(makeUser(dto, 1L, UserState.ACTIVE, now));
        when(userMapper.mapToDto(makeUser(dto, 1L, UserState.ACTIVE, now)))
                .thenReturn(makeUserDto(dto, 1L, UserState.ACTIVE, now));

        mvc.perform(post("/users")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));

        verify(userService).saveUser(makeUser(dto, null, UserState.ACTIVE, now));
        verify(userMapper).mapToUser(dto);
        verify(userMapper).mapToDto(makeUser(dto, 1L, UserState.ACTIVE, now));
    }

    @Test
    void testSaveNewUserWhenNoBody() throws Exception {
        mvc.perform(post("/users")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testSaveNewUserWhenNoEmail() throws Exception {
        mvc.perform(post("/users")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetAllUsers() throws Exception {
        final Instant johnDate = Instant.ofEpochMilli(1596978600000L);
        final Instant adrianDate = Instant.ofEpochMilli(1599743400000L);
        final Instant lukeDate = Instant.ofEpochMilli(1602421800000L);
        final User john = makeUser(1L, "John", "Doe", "jdoe@google.com", UserState.ACTIVE, johnDate);
        final User adrian = makeUser(2L, "Adrian", "Paul", "paul@aol.com", UserState.BLOCKED, adrianDate);
        final User luke = makeUser(3L, "Luke", "Skywalker", "jedi@yahoo.com", UserState.DELETED, lukeDate);
        final String expectedJson = "[{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\","
                + "\"email\":\"jdoe@google.com\",\"state\":\"ACTIVE\",\"registrationDate\":\"2020-08-09, 13:10:00\"},"
                + "{\"id\":2,\"firstName\":\"Adrian\",\"lastName\":\"Paul\",\"email\":\"paul@aol.com\","
                + "\"state\":\"BLOCKED\",\"registrationDate\":\"2020-09-10, 13:10:00\"},{\"id\":3,"
                + "\"firstName\":\"Luke\",\"lastName\":\"Skywalker\",\"email\":\"jedi@yahoo.com\","
                + "\"state\":\"DELETED\",\"registrationDate\":\"2020-10-11, 13:10:00\"}]";

        when(userService.getAllUsers()).thenReturn(List.of(john, adrian, luke));
        when(userMapper.mapToDto(List.of(john, adrian, luke))).thenReturn(List.of(makeUserDto(john),
                makeUserDto(adrian), makeUserDto(luke)));

        mvc.perform(get("/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));

        verify(userService).getAllUsers();
    }

    private NewUserDto makeNewUserDto(final String firstName, final String lastName, final String email) {
        final NewUserDto dto = new NewUserDto();
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setEmail(email);
        return dto;
    }

    private User makeUser(final NewUserDto dto, final Long id, final UserState state, final Instant registrationDate) {
        return makeUser(id, dto.getFirstName(), dto.getLastName(), dto.getEmail(), state, registrationDate);
    }

    private User makeUser(final Long id, final String firstName, final String lastName, final String email,
            final UserState state, final Instant registrationDate) {
        final User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setState(state);
        user.setRegistrationDate(registrationDate);
        return user;
    }

    private UserDto makeUserDto(final NewUserDto dto, final Long id, final UserState state,
            final Instant registrationDate) {
        final UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setFirstName(dto.getFirstName());
        userDto.setLastName(dto.getLastName());
        userDto.setEmail(dto.getEmail());
        userDto.setState(state.name());
        userDto.setRegistrationDate(registrationDate);
        return userDto;
    }

    private UserDto makeUserDto(final User user) {
        final UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setState(user.getState().name());
        dto.setRegistrationDate(user.getRegistrationDate());
        return dto;
    }
}