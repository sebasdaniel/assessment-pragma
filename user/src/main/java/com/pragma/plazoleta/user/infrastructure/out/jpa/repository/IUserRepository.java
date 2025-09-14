package com.pragma.plazoleta.user.infrastructure.out.jpa.repository;

import com.pragma.plazoleta.user.infrastructure.out.jpa.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);
}