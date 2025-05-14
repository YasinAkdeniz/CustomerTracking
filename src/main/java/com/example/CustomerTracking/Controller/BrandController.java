package com.example.CustomerTracking.Controller;

import com.example.CustomerTracking.Entity.Brand;
import com.example.CustomerTracking.Service.BrandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brands")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public List<Brand> getAll() {
        return brandService.getAllBrands();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Brand> getById(@PathVariable Long id) {
        return ResponseEntity.of(brandService.getBrandById(id));
    }

    @PostMapping
    public Brand create(@RequestBody Brand brand) {
        return brandService.saveBrand(brand);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Brand> update(@PathVariable Long id, @RequestBody Brand updated) {
        return ResponseEntity.ok(brandService.updateBrand(id, updated));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        brandService.deleteBrand(id);
    }
}
