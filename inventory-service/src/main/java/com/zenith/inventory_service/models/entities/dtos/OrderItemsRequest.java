package com.zenith.inventory_service.models.entities.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderItemsRequest {

    private Long id;

    private String sku;
    private Double price;
    private Long quantity;
}
