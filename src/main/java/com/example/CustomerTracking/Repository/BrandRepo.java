package com.example.CustomerTracking.Repository;

import com.example.CustomerTracking.Entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepo extends JpaRepository<Brand, Long> {
}
