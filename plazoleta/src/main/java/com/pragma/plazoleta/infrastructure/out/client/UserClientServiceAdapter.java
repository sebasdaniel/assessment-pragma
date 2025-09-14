package com.pragma.plazoleta.infrastructure.out.client;

import com.pragma.plazoleta.domain.spi.IUserServicePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

public class UserClientServiceAdapter implements IUserServicePort {

    @Value("${external-apis.user.get-user.url}")
    private String getUserUrl;

    @Override
    public String getUserRole(Long userId) {
        RestTemplate restTemplate = new RestTemplate();

        GetUserResponse user = restTemplate.getForObject(getUserUrl + "/" + userId, GetUserResponse.class);

        return user != null ? user.getRole() : "";
    }
}
