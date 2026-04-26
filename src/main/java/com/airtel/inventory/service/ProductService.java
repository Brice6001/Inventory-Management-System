package com.airtel.inventory.service;

import org.springframework.stereotype.Service;
import java.util.List;
import com.airtel.inventory.model.Product;
import com.airtel.inventory.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public List<Product> getAll() {
        return repo.findAll();
    }

    public void save(Product p) {
        repo.save(p);
    }

    public Product get(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}