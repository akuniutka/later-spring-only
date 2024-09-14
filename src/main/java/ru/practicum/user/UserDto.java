package ru.practicum.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@Data
@EqualsAndHashCode(of = "id")
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String state;

    @JsonFormat(pattern = "yyyy-MM-dd, hh:mm:ss")
    private Instant registrationDate;
}
