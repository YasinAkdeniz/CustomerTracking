package com.example.CustomerTracking.Repository;

import com.example.CustomerTracking.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {
}
