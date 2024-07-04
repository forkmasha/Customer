package com.example.restservice.repository;

import com.example.restservice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomersRepository extends JpaRepository<Customer, Long> {

}
