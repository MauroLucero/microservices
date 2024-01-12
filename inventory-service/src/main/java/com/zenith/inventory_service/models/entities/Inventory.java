package com.zenith.inventory_service.models.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="inventory")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sku;
    private Long quantity;
}
