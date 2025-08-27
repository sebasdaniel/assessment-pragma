package com.pragma.plazoleta.application.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantResponseDto {
    private Long id;
    private Integer nit;
    private String name;
    private String address;
    private String phoneNumber;
    private String urlLogo;
    private Long ownerId;
}
