package com.pragma.plazoleta.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Dish {
    private Long id;
    private String name;
    private Integer price;
    private String description;
    private String urlImage;
    private String category;
    private Long restaurantId;
    private boolean active;
}
