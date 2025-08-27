package com.pragma.plazoleta.application.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantRequestDto {
    private Integer nit;
    private String name;
    private String address;
    private String phoneNumber;
    private String urlLogo;
    private Long ownerId;
}
