package com.pragma.plazoleta.user.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?\\d{1,12}$");

    private Long id;
    private String name;
    private String lastName;
    private Integer idNumber;
    private String phoneNumber;
    private LocalDate birthdate;
    private String email;
    private String password;
    private String rol;

    public boolean isValidEmail() {
        return this.email.contains("@");
    }

    public boolean isValidPhoneNumber() {
        return PHONE_PATTERN.matcher(this.phoneNumber).matches();
    }

    public int getAge() {
        LocalDate currentDate = LocalDate.now();
        if (birthdate.isAfter(currentDate)) {
            throw new IllegalArgumentException("Birthdate cannot be in the future.");
        }

        Period period = Period.between(birthdate, currentDate);
        return period.getYears();
    }

    public boolean hasLegalAge() {
        return getAge() >= 18;
    }
}
