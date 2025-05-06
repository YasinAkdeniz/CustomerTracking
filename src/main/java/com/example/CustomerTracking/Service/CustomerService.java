package com.example.CustomerTracking.Service;

import com.example.CustomerTracking.Entity.Customer;
import com.example.CustomerTracking.Repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    public List<Customer> getAllUser() {
        return customerRepo.findAll();
    }

}
