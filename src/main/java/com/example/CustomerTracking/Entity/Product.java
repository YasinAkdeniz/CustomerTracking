package com.example.CustomerTracking.Entity;

import com.example.CustomerTracking.Enum.ProductType;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "stock_amount", nullable = false)
    private Integer stockAmount;

    @Column(name = "product_type", nullable = false)
    private String productType;

    @Column(name = "product_sub_type")
    private String productSubType;
}
