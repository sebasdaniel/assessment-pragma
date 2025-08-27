package com.pragma.plazoleta.user.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserRequestDto {
    private String name;
    private String lastName;
    private Integer idNumber;
    private String phoneNumber;
    private LocalDate birthdate;
    private String email;
    private String password;
    private String role;
}
