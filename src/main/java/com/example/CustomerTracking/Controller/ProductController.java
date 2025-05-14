package com.example.CustomerTracking.Controller;

import com.example.CustomerTracking.Entity.Customer;
import com.example.CustomerTracking.Entity.Product;
import com.example.CustomerTracking.Repository.ProductRepo;
import com.example.CustomerTracking.Service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;
    private final ProductRepo productRepo;

    public ProductController(ProductService service, ProductRepo productRepo) {
        this.service = service;
        this.productRepo = productRepo;
    }

    @GetMapping
    public List<Product> getAll() {
        return service.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return ResponseEntity.of(service.getProductById(id));
    }

    @GetMapping("/search")
    public List<Product> searchProduct(@RequestParam String fullName) {
        return productRepo.findByFullNameContainingIgnoreCase(fullName);
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return service.saveProduct(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product updated) {
        return ResponseEntity.ok(service.updateProduct(id, updated));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteProduct(id);
    }
}
