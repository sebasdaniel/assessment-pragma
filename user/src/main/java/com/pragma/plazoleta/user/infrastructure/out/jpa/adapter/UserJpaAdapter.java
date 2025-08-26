package com.pragma.plazoleta.user.infrastructure.out.jpa.adapter;

import com.pragma.plazoleta.user.domain.model.User;
import com.pragma.plazoleta.user.domain.spi.IUserPersistencePort;
import com.pragma.plazoleta.user.infrastructure.exception.NoDataFoundException;
import com.pragma.plazoleta.user.infrastructure.out.jpa.entity.ObjectEntity;
import com.pragma.plazoleta.user.infrastructure.out.jpa.mapper.IObjectEntityMapper;
import com.pragma.plazoleta.user.infrastructure.out.jpa.repository.IObjectRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IObjectRepository objectRepository;
    private final IObjectEntityMapper objectEntityMapper;


    @Override
    public User saveUser(User user) {
        ObjectEntity objectEntity = objectRepository.save(objectEntityMapper.toEntity(user));
        return objectEntityMapper.toObjectModel(objectEntity);
    }

    @Override
    public List<User> getAllUsers() {
        List<ObjectEntity> entityList = objectRepository.findAll();
        if (entityList.isEmpty()) {
            throw new NoDataFoundException();
        }
        return objectEntityMapper.toObjectModelList(entityList);
    }
}