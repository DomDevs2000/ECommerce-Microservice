package com.DomDevs.inventoryservice.service;


import com.DomDevs.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    public boolean isInStock(String skuCode){

        inventoryRepository.findBySkuCode(skuCode);

    }

}
