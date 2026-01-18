package com.javanauta.customer.business;

import com.javanauta.customer.business.converter.CustomerConverter;
import com.javanauta.customer.business.dto.CustomerDTO;
import com.javanauta.customer.infrastructure.entity.Customer;
import com.javanauta.customer.infrastructure.exceptions.ConflictException;
import com.javanauta.customer.infrastructure.exceptions.ResourceNotFoundException;
import com.javanauta.customer.infrastructure.security.JwtUtil;
import com.javanauta.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service

public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerConverter customerConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        emailExists(customerDTO.getEmail());
        customerDTO.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        Customer customer = customerConverter.toCustomer(customerDTO);
        customer = customerRepository.save(customer);
        return customerConverter.toCustomerDTO(customer);
    }

    public void emailExists(String email) {
        try {

            boolean exists = checkExistingEmail(email);
            if (exists) {
                throw new ConflictException("Email already registered" + email);

            }
        } catch (ConflictException e) {
            throw new ConflictException("Email already registered", e.getCause());
        }

    }

    public boolean checkExistingEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    public Customer searchCustomerByEmail(String email) {
        return customerRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email not found" + email));
    }

    public void deleteCustomerByEmail(String email) {
        customerRepository.deleteByEmail(email);
    }

    public CustomerDTO updateDataCustomer(String token, CustomerDTO dto) {

        //Aqui buscamos o email do "customer" através do token (tirar a obrigatoriedade do email)
        String email = jwtUtil.extractUsername(token.substring(7));

        //Criptografia de "Password"
        dto.setPassword(dto.getPassword() != null ? passwordEncoder.encode(dto.getPassword()) : null);

        //Aqui busca os dados do "customer" no banco de dados
        Customer customerEntity = customerRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email address not found"));

        //Mesclou os dados que recebemos na requisição DTO com os dados do banco de dados
        Customer customer = customerConverter.updateCustomer(dto, customerEntity);

        //Salvou os dados do "customer" convertido e depois pegou o retorno e converteu para CustomerDTO
        return customerConverter.toCustomerDTO(customerRepository.save(customer));
    }
}