package com.example.CustomerTracking.Service;


import com.example.CustomerTracking.Entity.Product;
import com.example.CustomerTracking.Repository.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepo.findById(id);
    }

    public Product saveProduct(Product product) {
        return productRepo.save(product);
    }

    public Product updateProduct(Long id, Product updated) {
        Product existing = productRepo.findById(id).orElseThrow();
        existing.setFullName(updated.getFullName());
        existing.setBrand(updated.getBrand());
        existing.setPrice(updated.getPrice());
        existing.setStockAmount(updated.getStockAmount());
        existing.setProductType(updated.getProductType());
        existing.setProductSubType(updated.getProductSubType());
        return productRepo.save(existing);
    }

    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }
}

