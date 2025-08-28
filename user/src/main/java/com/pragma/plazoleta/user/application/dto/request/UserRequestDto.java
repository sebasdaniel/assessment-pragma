package com.pragma.plazoleta.user.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class UserRequestDto {

    @NotBlank(message = "The name field is required")
    private String name;

    @NotBlank(message = "The last name field is required")
    private String lastName;

    @NotNull(message = "The identification number field is required")
    private Integer idNumber;

    @NotBlank(message = "The phone number field is required")
    private String phoneNumber;

    private LocalDate birthdate;

    @NotNull(message = "The email field is required")
    @Email(message = "The email format is not valid")
    private String email;

    @NotBlank(message = "The password field is required")
    private String password;

    private String role;
}
