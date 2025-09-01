package com.pragma.plazoleta.infrastructure.out.client;

import com.pragma.plazoleta.domain.spi.IUserServicePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

public class UserClientServiceAdapter implements IUserServicePort {

    @Value("${externalapis.user.getuser.url}")
    private String getUserUrl;

    @Override
    public String getUserRole(Long userId) {
        RestTemplate restTemplate = new RestTemplate();

        //TODO: manage exception with custom exception
        GetUserResponse user = restTemplate.getForObject(getUserUrl + "/" + userId, GetUserResponse.class);

        return user != null ? user.getRole() : "";
    }
}
