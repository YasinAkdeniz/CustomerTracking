package com.example.CustomerTracking.Repository;

import com.example.CustomerTracking.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface    CustomerRepo extends JpaRepository<Customer, Long> {
}
