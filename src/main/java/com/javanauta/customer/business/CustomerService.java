package com.javanauta.customer.business;

import com.javanauta.customer.business.converter.CustomerConverter;
import com.javanauta.customer.business.dto.AddressDTO;
import com.javanauta.customer.business.dto.CustomerDTO;
import com.javanauta.customer.business.dto.PhoneDTO;
import com.javanauta.customer.infrastructure.entity.Address;
import com.javanauta.customer.infrastructure.entity.Customer;
import com.javanauta.customer.infrastructure.entity.Phone;
import com.javanauta.customer.infrastructure.exceptions.ConflictException;
import com.javanauta.customer.infrastructure.exceptions.ResourceNotFoundException;
import com.javanauta.customer.infrastructure.security.JwtUtil;
import com.javanauta.customer.repository.AddressRepository;
import com.javanauta.customer.repository.CustomerRepository;
import com.javanauta.customer.repository.PhoneRepository;
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
    private final AddressRepository addressRepository;
    private final PhoneRepository phoneRepository;

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

    public CustomerDTO searchCustomerByEmail(String email) {
        try {
            return customerConverter.toCustomerDTO(
                    customerRepository.findByEmail(email).orElseThrow(
                    () -> new ResourceNotFoundException("Email not found " + email)));

        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Email not found " + email);
        }
    }

    public void deleteCustomerByEmail(String email) {
        customerRepository.deleteByEmail(email);
    }

    public CustomerDTO updateDataCustomer(String token, CustomerDTO dto) {

        //Aqui buscamos o email do "customer" através do token (tirar a obrigatoriedade do email)
        String email = jwtUtil.extractTokenEmail(token.substring(7));

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

    public AddressDTO updateAddress(Long idAddress, AddressDTO addressDTO){

        Address entity = addressRepository.findById(idAddress).orElseThrow(() ->
                new ResourceNotFoundException("Id not found " + idAddress));

        Address address = customerConverter.updateAddress(addressDTO, entity);

        return customerConverter.toAddressDTO(addressRepository.save(address));
    }

    public PhoneDTO updatePhone(Long idPhone, PhoneDTO dto){

        Phone entity = phoneRepository.findById(idPhone).orElseThrow(() ->
                new ResourceNotFoundException("Phone not found " + idPhone));

        Phone phone = customerConverter.updatePhone(dto, entity);

        return customerConverter.toPhoneDTO(phoneRepository.save(phone));
    }

    public AddressDTO registerAddress(String token, AddressDTO dto){
        String email = jwtUtil.extractTokenEmail(token.substring(7));
        Customer customer = customerRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email not found " + email));

        Address address = customerConverter.toAddressEntity(dto, customer.getId());
        Address addressEntity = addressRepository.save(address);
        return customerConverter.toAddressDTO(addressEntity);
    }
    public PhoneDTO registerPhone(String token, PhoneDTO dto){
        String email = jwtUtil.extractTokenEmail(token.substring(7));
        Customer customer = customerRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email not found " + email));


        Phone phone = customerConverter.toPhoneEntity(dto, customer.getId());
        Phone phoneEntity = phoneRepository.save(phone);
        return customerConverter.toPhoneDTO(phoneEntity);

    }
}