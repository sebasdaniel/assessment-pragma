package com.pragma.plazoleta.application.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeDishStatusRequestDto {
    @NotNull(message = "Must provide the dish ID")
    @Min(value = 1, message = "Dish ID must be bigger than zero")
    private Long id;

    @NotNull(message = "Must provide the dish status active")
    private Boolean active;

    private Long callerId;
}
