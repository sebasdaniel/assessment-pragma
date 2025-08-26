package com.pragma.plazoleta.user.infrastructure.configuration;

import com.pragma.plazoleta.user.domain.api.IUserServicePort;
import com.pragma.plazoleta.user.domain.spi.IUserPersistencePort;
import com.pragma.plazoleta.user.domain.usecase.UserUseCase;
import com.pragma.plazoleta.user.infrastructure.out.jpa.adapter.UserJpaAdapter;
import com.pragma.plazoleta.user.infrastructure.out.jpa.mapper.IObjectEntityMapper;
import com.pragma.plazoleta.user.infrastructure.out.jpa.repository.IObjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IObjectRepository objectRepository;
    private final IObjectEntityMapper objectEntityMapper;

    @Bean
    public IUserPersistencePort objectPersistencePort() {
        return new UserJpaAdapter(objectRepository, objectEntityMapper);
    }

    @Bean
    public IUserServicePort objectServicePort() {
        return new UserUseCase(objectPersistencePort());
    }
}