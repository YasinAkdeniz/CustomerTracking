package com.example.CustomerTracking.Service;

import com.example.CustomerTracking.Entity.Brand;
import com.example.CustomerTracking.Repository.BrandRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService {

    private final BrandRepo brandRepo;

    public BrandService(BrandRepo brandRepo) {
        this.brandRepo = brandRepo;
    }

    public List<Brand> getAllBrands() {
        return brandRepo.findAll();
    }

    public Optional<Brand> getBrandById(Long id) {
        return brandRepo.findById(id);
    }

    public Brand saveBrand(Brand brand) {
        return brandRepo.save(brand);
    }

    public Brand updateBrand(Long id, Brand updated) {
        Brand existing = brandRepo.findById(id).orElseThrow();
        existing.setName(updated.getName());
        return brandRepo.save(existing);
    }

    public void deleteBrand(Long id) {
        brandRepo.deleteById(id);
    }
}
