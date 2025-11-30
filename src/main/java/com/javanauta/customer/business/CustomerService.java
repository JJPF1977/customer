package com.javanauta.customer.business;

import com.javanauta.customer.business.converter.CustomerConverter;
import com.javanauta.customer.business.dto.CustomerDTO;
import com.javanauta.customer.infrastructure.entity.Customer;
import com.javanauta.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerConverter customerConverter;

    public CustomerDTO customerSave(CustomerDTO customerDTO){
        Customer customer = customerConverter.toCustomer(customerDTO);
        customer = customerRepository.save(customer);
        return customerConverter.toCustomerDTO(customer);
    }
}
