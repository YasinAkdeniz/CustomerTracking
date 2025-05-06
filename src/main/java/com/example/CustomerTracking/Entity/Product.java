package com.example.CustomerTracking.Entity;

import com.example.CustomerTracking.Enum.ProductType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Products")

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "stock_amount", nullable = false)
    private Integer stockAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type" ,nullable = false)
    private ProductType type;
}
