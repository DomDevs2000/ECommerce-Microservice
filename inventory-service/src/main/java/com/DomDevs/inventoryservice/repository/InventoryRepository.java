package com.DomDevs.inventoryservice.repository;

import com.DomDevs.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InventoryRepository extends JpaRepository<Inventory, Long> {


}
