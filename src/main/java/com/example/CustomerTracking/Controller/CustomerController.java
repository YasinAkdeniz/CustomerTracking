package com.example.CustomerTracking.Controller;

import com.example.CustomerTracking.Entity.Customer;
import com.example.CustomerTracking.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers")
    @ResponseBody
    public List<Customer> getAllCustomer() {
        return customerService.getAllUser();
    }
}
