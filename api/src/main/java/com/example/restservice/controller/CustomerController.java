package com.example.restservice.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import com.example.restservice.service.CustomersService;
import com.example.restservice.model.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomersService customerService;

    public CustomerController(CustomersService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest customerRequest) {
        try {

            validateCustomerRequest(customerRequest);

            Customer customer = new Customer(customerRequest);
            Customer createdCustomer = customerService.createCustomer(customer);

            CustomerResponse response = new CustomerResponse(createdCustomer);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {

        List<Customer> customers = customerService.getAllCustomers();
        List<CustomerResponse> responses = new ArrayList<>();

        for (Customer customer : customers) {
            CustomerResponse response = new CustomerResponse(customer);
            responses.add(response);
        }

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customerOptional = customerService.getCustomerById(id);

        if (customerOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Customer customer = customerOptional.get();
        CustomerResponse response = new CustomerResponse(customer);
        return ResponseEntity.ok(response);
    }

    private void validateCustomerRequest(CustomerRequest customerRequest) {

        if (customerRequest.getFullName() == null || customerRequest.getFullName().length() < 2 || customerRequest.getFullName().length() > 50) {
            throw new IllegalArgumentException("Full name must be between 2 and 50 characters long");
        }

        if (customerRequest.getEmail() == null || customerRequest.getEmail().length() < 2 || customerRequest.getEmail().length() > 100) {
            throw new IllegalArgumentException("Email must be between 2 and 100 characters long");
        }

        if (!customerRequest.getEmail().contains("@")) {
            throw new IllegalArgumentException("Email must contain '@' symbol");
        }

        if (customerService.isEmailExists(customerRequest.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        if (customerRequest.getPhone() != null) {
            if (customerRequest.getPhone().length() < 6 || customerRequest.getPhone().length() > 14) {
                throw new IllegalArgumentException("Phone number must be between 6 and 14 characters long");
            }
            if (!customerRequest.getPhone().startsWith("+")) {
                throw new IllegalArgumentException("Phone number must start with '+'");
            }
            if (!customerRequest.getPhone().substring(1).matches("\\d+")) {
                throw new IllegalArgumentException("Phone number must contain only digits");
            }
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        try {
            Optional<Customer> existingCustomer = customerService.getCustomerById(id);
            if (existingCustomer.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Customer customer = existingCustomer.get();
            customer.setActive(false);
            customerService.updateCustomer(customer);

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id, @RequestBody CustomerRequest customerRequest) {
        try {

            Optional<Customer> customerOptional = customerService.getCustomerById(id);
            if (customerOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            validateCustomerRequest(customerRequest);

            Customer customer = new Customer(customerRequest);
            Customer updatedCustomer = customerService.updateCustomer(customer);

            CustomerResponse response = new CustomerResponse(updatedCustomer);

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}