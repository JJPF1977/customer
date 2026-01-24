package com.javanauta.customer.controller;

import com.javanauta.customer.business.CustomerService;
import com.javanauta.customer.business.dto.AddressDTO;
import com.javanauta.customer.business.dto.CustomerDTO;
import com.javanauta.customer.business.dto.PhoneDTO;
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
    public ResponseEntity<CustomerDTO> searchCustomerByEmail(@RequestParam("email") String email){
        return ResponseEntity.ok(customerService.searchCustomerByEmail(email));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteCustomerByEmail(@PathVariable String email){
        customerService.deleteCustomerByEmail(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<CustomerDTO> updCustomer(@RequestBody CustomerDTO dto,
                                                   @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(customerService.updateDataCustomer(token, dto));
    }

    @PutMapping("/address")
    public ResponseEntity<AddressDTO> updateAddress(@RequestBody AddressDTO dto,
                                                    @RequestParam("id") Long id){
        return ResponseEntity.ok(customerService.updateAddress(id, dto));
    }

    @PutMapping("/phone")
    public ResponseEntity<PhoneDTO> updatePhone(@RequestBody PhoneDTO dto,
                                                    @RequestParam("id") Long id){
        return ResponseEntity.ok(customerService.updatePhone(id, dto));
    }

}
