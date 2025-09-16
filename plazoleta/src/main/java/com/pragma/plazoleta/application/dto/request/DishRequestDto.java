package com.pragma.plazoleta.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DishRequestDto {
    @NotBlank(message = "Must provide a dish name")
    private String name;

    @NotNull(message = "Must provide a dish price")
    @Min(value = 1, message = "Dish price must be bigger than zero")
    private Integer price;

    @NotBlank(message = "Must provide a dish description")
    private String description;

    @NotBlank(message = "Must provide a dish image url")
    private String urlImage;

    @NotBlank(message = "Must provide a dish category")
    private String category;

    @NotNull(message = "Must provide a restaurant id that the dish belongs to")
    private Long restaurantId;

    private Long callerId;
}
