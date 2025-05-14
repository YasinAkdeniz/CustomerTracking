package com.example.CustomerTracking.Repository;

import com.example.CustomerTracking.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {

    List<Product> findByFullNameContainingIgnoreCase(String fullName);
}
