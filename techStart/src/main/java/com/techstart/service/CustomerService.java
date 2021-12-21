package com.techstart.service;

import com.techstart.model.Customer;
import com.techstart.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/*
 * Author: Renso Valencia Ventura
 * Date: 21/12/2021
 * Version: 1.0
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public void create(Customer customer)  {
        customerRepository.save(customer);
    }

    public List<Customer> findAll()  {
        return customerRepository.findAll();
    }
}