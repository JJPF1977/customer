package com.javanauta.customer.controller;

import com.javanauta.customer.business.CustomerService;
import com.javanauta.customer.business.dto.CustomerDTO;
import com.javanauta.customer.infrastructure.entity.Customer;
import com.javanauta.customer.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor

public class CustomerController {

    private final CustomerService customerService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<CustomerDTO> saveCustomer(@RequestBody CustomerDTO customerDTO){
        return ResponseEntity.ok(customerService.saveCustomer(customerDTO));
    }


    @PostMapping("/login")
    public String login(@RequestBody CustomerDTO customerDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(customerDTO.getEmail(),
                        customerDTO.getPassword())
        );
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }


    @GetMapping
    public ResponseEntity<Customer> searchCustomerByEmail(@RequestParam("email") String email){
        return ResponseEntity.ok(customerService.searchCustomerByEmail(email));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteCustomerByEmail(@PathVariable String email){
        customerService.deleteCustomerByEmail(email);
        return ResponseEntity.ok().build();
    }

}
