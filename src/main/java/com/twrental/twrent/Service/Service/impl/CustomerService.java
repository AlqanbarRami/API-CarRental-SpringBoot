package com.twrental.twrent.Service.Service.impl;

import com.twrental.twrent.Model.Customer;
import com.twrental.twrent.Repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements com.twrental.twrent.Service.CustomerService {

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository){
        super();
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> GetAllCustomers() {
        return customerRepository.findAll();
    }
}
