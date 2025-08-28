package com.pragma.plazoleta.user.infrastructure.configuration;

import com.pragma.plazoleta.user.domain.api.IUserServicePort;
import com.pragma.plazoleta.user.domain.spi.IPasswordEncoderPort;
import com.pragma.plazoleta.user.domain.spi.IUserPersistencePort;
import com.pragma.plazoleta.user.domain.usecase.UserUseCase;
import com.pragma.plazoleta.user.infrastructure.out.encoder.adapter.PasswordEncoderAdapter;
import com.pragma.plazoleta.user.infrastructure.out.jpa.adapter.UserJpaAdapter;
import com.pragma.plazoleta.user.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.plazoleta.user.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserJpaAdapter(userRepository, userEntityMapper);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public IPasswordEncoderPort bcryptEncoderPort() {
        return new PasswordEncoderAdapter(passwordEncoder());
    }

    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(userPersistencePort(), bcryptEncoderPort());
    }
}