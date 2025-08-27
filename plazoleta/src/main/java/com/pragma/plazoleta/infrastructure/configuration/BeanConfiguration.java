package com.pragma.plazoleta.infrastructure.configuration;

import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.pragma.plazoleta.domain.spi.IUserServicePort;
import com.pragma.plazoleta.domain.usecase.RestaurantUseCase;
import com.pragma.plazoleta.infrastructure.out.client.UserClientServiceAdapter;
import com.pragma.plazoleta.infrastructure.out.jpa.adapter.RestaurantJpaAdapter;
import com.pragma.plazoleta.infrastructure.out.jpa.mapper.IObjectEntityMapper;
import com.pragma.plazoleta.infrastructure.out.jpa.repository.IObjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IObjectRepository objectRepository;
    private final IObjectEntityMapper objectEntityMapper;

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort() {
        return new RestaurantJpaAdapter(objectRepository, objectEntityMapper);
    }

    @Bean
    public IUserServicePort userServicePort() {
        return new UserClientServiceAdapter();
    }

    @Bean
    public IRestaurantServicePort objectServicePort() {
        return new RestaurantUseCase(restaurantPersistencePort(), userServicePort());
    }
}