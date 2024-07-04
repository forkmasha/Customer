package com.example.restservice.service;

import java.util.List;
import java.util.Optional;

import com.example.restservice.model.Customer;
import com.example.restservice.repository.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomersService {

    @Autowired
    private CustomersRepository customersRepository;

    public Customer createCustomer(Customer customer){
        return customersRepository.save(customer);
    }

    public boolean isEmailExists(String email) {
        List<Customer> customers = customersRepository.findAll();
        for (Customer customer : customers) {
            if (customer.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public List<Customer> getAllCustomers() {
        return customersRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customersRepository.findById(id);
    }
    public Customer updateCustomer(Customer existingCustomer) {
        return customersRepository.save(existingCustomer);
    }
}
