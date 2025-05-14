package com.example.CustomerTracking.Repository;

import com.example.CustomerTracking.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c WHERE CONCAT(c.name, ' ', c.surname) LIKE %:fullName%")
    List<Customer> searchByFullName(@Param("fullName") String name);

}
