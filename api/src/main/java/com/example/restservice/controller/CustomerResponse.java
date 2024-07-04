package com.example.restservice.controller;

import com.example.restservice.model.Customer;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;

    public CustomerResponse(Long id, String fullName, String email, String phone) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
    }
    public CustomerResponse(Customer customer){
        this.id= customer.getId();
        this.phone= customer.getPhone();
        this.email= customer.getEmail();
        this.fullName= customer.getFullName();
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}