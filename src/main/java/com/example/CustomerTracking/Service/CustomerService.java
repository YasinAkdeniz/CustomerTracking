package com.example.CustomerTracking.Service;

import com.example.CustomerTracking.Entity.Customer;
import com.example.CustomerTracking.Repository.CustomerRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {


    private final CustomerRepo customerRepo;

    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public List<Customer> getAllUser() {
        return customerRepo.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepo.findById(id).orElse(null);
    }

    public Customer createCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        return customerRepo.findById(id).map(existing -> {
            existing.setName(updatedCustomer.getName());
            existing.setSurname(updatedCustomer.getSurname());
            existing.setEmail(updatedCustomer.getEmail());
            existing.setPhoneNumber(updatedCustomer.getPhoneNumber());
            existing.setGender(updatedCustomer.getGender());
            existing.setIdentityNumber(updatedCustomer.getIdentityNumber());
            return customerRepo.save(existing);
        }).orElse(null);
    }

    public boolean deleteCustomer(Long id) {
        if (customerRepo.existsById(id)) {
            customerRepo.deleteById(id);
            return true;
        }
        return false;
    }

}
