package com.pragma.plazoleta.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RestaurantRequestDto {

    @NotNull(message = "Must provide a NIT")
    @Min(value = 111111111, message = "Wrong NIT number")
    @Max(value = 999999, message = "Wrong NIT number")
    private Integer nit;

    @NotBlank(message = "Must provide a name")
    private String name;

    @NotBlank(message = "Must provide an address")
    private String address;

    @NotBlank(message = "Must provide a phone number")
    private String phoneNumber;

    @NotBlank(message = "Must provide an url logo")
    private String urlLogo;

    @NotNull(message = "Must provide the owner ID")
    private Long ownerId;
}
