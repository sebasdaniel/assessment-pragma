package com.pragma.plazoleta.user.infrastructure.out.jpa.repository;

import com.pragma.plazoleta.user.infrastructure.out.jpa.entity.ObjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IObjectRepository extends JpaRepository<ObjectEntity, Long> {

}