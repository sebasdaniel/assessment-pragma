package com.pragma.plazoleta.user.domain.spi;

import com.pragma.plazoleta.user.domain.model.ObjectModel;
import java.util.List;

public interface IObjectPersistencePort {
    ObjectModel saveObject(ObjectModel objectModel);

    List<ObjectModel> getAllObjects();
}