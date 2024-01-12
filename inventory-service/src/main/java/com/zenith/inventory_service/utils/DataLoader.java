package com.zenith.inventory_service.utils;

import com.zenith.inventory_service.models.entities.Inventory;
import com.zenith.inventory_service.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final InventoryRepository inventoryRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Loading data...");
        if(inventoryRepository.findAll().size() == 0){
            inventoryRepository.saveAll(
                    List.of(
                            Inventory.builder().sku("00000001").quantity(10L).build(),
                            Inventory.builder().sku("00000002").quantity(20L).build(),
                            Inventory.builder().sku("00000003").quantity(30L).build(),
                            Inventory.builder().sku("00000004").quantity(0L).build()
                    )
            );
        }
        log.info("Data loaded.");
    }
}
