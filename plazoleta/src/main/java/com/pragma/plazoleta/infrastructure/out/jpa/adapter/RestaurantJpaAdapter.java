package com.pragma.plazoleta.infrastructure.out.jpa.adapter;

import com.pragma.plazoleta.domain.model.Restaurant;
import com.pragma.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.pragma.plazoleta.infrastructure.exception.NoDataFoundException;
import com.pragma.plazoleta.infrastructure.out.jpa.entity.ObjectEntity;
import com.pragma.plazoleta.infrastructure.out.jpa.mapper.IObjectEntityMapper;
import com.pragma.plazoleta.infrastructure.out.jpa.repository.IObjectRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RestaurantJpaAdapter implements IRestaurantPersistencePort {

    private final IObjectRepository objectRepository;
    private final IObjectEntityMapper objectEntityMapper;


    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) {
        ObjectEntity objectEntity = objectRepository.save(objectEntityMapper.toEntity(restaurant));
        return objectEntityMapper.toObjectModel(objectEntity);
    }

    @Override
    public List<Restaurant> getAllObjects() {
        List<ObjectEntity> entityList = objectRepository.findAll();
        if (entityList.isEmpty()) {
            throw new NoDataFoundException();
        }
        return objectEntityMapper.toObjectModelList(entityList);
    }
}