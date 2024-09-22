package ru.practicum.user;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.common.BaseController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController extends BaseController {

    private final UserService userService;
    private final UserMapper mapper;

    @GetMapping
    public List<UserDto> getAllUsers(
            final HttpServletRequest request
    ) {
        logRequest(request);
        final List<UserDto> dtos = mapper.mapToDto(userService.getAllUsers());
        logResponse(request, dtos);
        return dtos;
    }

    @PostMapping
    public UserDto saveNewUser(
            @RequestBody final NewUserDto newUserDto,
            final HttpServletRequest request
    ) {
        logRequest(request, newUserDto);
        final User user = mapper.mapToUser(newUserDto);
        final UserDto dto = mapper.mapToDto(userService.saveUser(user));
        logResponse(request, dto);
        return dto;
    }
}
