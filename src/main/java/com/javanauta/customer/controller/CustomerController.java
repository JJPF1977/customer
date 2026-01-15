package com.javanauta.customer.controller;

import com.javanauta.customer.business.CustomerService;
import com.javanauta.customer.business.dto.CustomerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController("/customer")
@RequiredArgsConstructor

public class CustomerController {

    private final CustomerService customerService;
    @PostMapping
    public ResponseEntity<CustomerDTO> customerSave(@RequestBody CustomerDTO customerDTO){
       return ResponseEntity.ok(customerService.customerSave(customerDTO));

    }

}
