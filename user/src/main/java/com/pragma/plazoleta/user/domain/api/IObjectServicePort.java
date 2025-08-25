package com.pragma.plazoleta.user.domain.api;

import com.pragma.plazoleta.user.domain.model.ObjectModel;

import java.util.List;

public interface IObjectServicePort {

    void saveObject(ObjectModel objectModel);

    List<ObjectModel> getAllObjects();
}