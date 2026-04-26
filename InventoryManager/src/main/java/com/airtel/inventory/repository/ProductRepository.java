package com.airtel.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.airtel.inventory.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}