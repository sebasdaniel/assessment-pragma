package com.pragma.plazoleta.user.infrastructure.out.jpa.adapter;

import com.pragma.plazoleta.user.domain.model.User;
import com.pragma.plazoleta.user.domain.spi.IUserPersistencePort;
import com.pragma.plazoleta.user.infrastructure.exception.NoDataFoundException;
import com.pragma.plazoleta.user.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.plazoleta.user.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.plazoleta.user.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    @Override
    public User saveUser(User user) {
        UserEntity userEntity = userRepository.save(userEntityMapper.toEntity(user));
        return userEntityMapper.toModel(userEntity);
    }

    @Override
    public List<User> getAllUsers() {
        List<UserEntity> entityList = userRepository.findAll();
        if (entityList.isEmpty()) {
            throw new NoDataFoundException();
        }
        return userEntityMapper.toObjectModelList(entityList);
    }
}