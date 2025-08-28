package com.pragma.plazoleta.user.domain.model;

import java.time.LocalDate;
import java.time.Period;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    private String name;
    private String lastName;
    private Integer idNumber;
    private String phoneNumber;
    private LocalDate birthdate;
    private String email;
    private String password;
    private String role;

    public int getAge() {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(birthdate, currentDate);

        return period.getYears();
    }
}
