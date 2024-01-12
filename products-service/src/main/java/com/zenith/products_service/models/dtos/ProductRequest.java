package com.zenith.products_service.models.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductRequest {

    private String sku;
    private String name;
    private String description;
    private Double price;
    private Boolean status;
}
