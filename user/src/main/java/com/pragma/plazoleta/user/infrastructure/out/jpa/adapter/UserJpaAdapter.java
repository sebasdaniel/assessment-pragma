package com.pragma.plazoleta.user.infrastructure.out.jpa.adapter;

import com.pragma.plazoleta.user.domain.model.User;
import com.pragma.plazoleta.user.domain.spi.IUserPersistencePort;
import com.pragma.plazoleta.user.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.plazoleta.user.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.plazoleta.user.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    @Override
    public User saveUser(User user) {
        UserEntity userEntity = userRepository.save(userEntityMapper.toEntity(user));
        return userEntityMapper.toModel(userEntity);
    }
}