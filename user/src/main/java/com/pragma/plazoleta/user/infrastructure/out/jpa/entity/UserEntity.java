package com.pragma.plazoleta.user.infrastructure.out.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(length = 30)
    private String name;

    @Column(length = 30)
    private String lastName;

    private Integer idNumber;

    @Column(length = 13)
    private String phoneNumber;

    private LocalDate birthdate;

    @Column(length = 50, unique = true)
    private String email;

    private String password;

    @Column(length = 20)
    private String role;
}
