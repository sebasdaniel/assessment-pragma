package com.pragma.plazoleta.infrastructure.out.client;

import com.pragma.plazoleta.domain.spi.IUserServicePort;

public class UserClientServiceAdapter implements IUserServicePort {
    @Override
    public boolean userExists(Long id) {
        //TODO: implement method
        return false;
    }
}
