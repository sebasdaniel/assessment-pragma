package com.pragma.plazoleta.application.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDishRequestDto {
    @NotNull(message = "Must provide the dish ID")
    @Min(value = 1, message = "Dish ID must be bigger than zero")
    private Long id;

    @NotNull(message = "Must provide a dish price")
    @Min(value = 1, message = "Dish price must be bigger than zero")
    private Integer price;

    @NotBlank(message = "Must provide a dish description")
    private String description;
}
