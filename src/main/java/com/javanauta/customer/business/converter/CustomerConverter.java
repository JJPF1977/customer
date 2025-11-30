package com.javanauta.customer.business.converter;

import com.javanauta.customer.business.dto.AddressDTO;
import com.javanauta.customer.business.dto.CustomerDTO;
import com.javanauta.customer.business.dto.FoneDTO;
import com.javanauta.customer.infrastructure.entity.Address;
import com.javanauta.customer.infrastructure.entity.Customer;
import com.javanauta.customer.infrastructure.entity.Fone;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerConverter {

    public Customer toCustomer(CustomerDTO customerDTO){
        return Customer.builder()
                .name(customerDTO.getName())
                .email(customerDTO.getEmail())
                .password(customerDTO.getPassword())
                .addresses(toAddressList(customerDTO.getAddresses()))
                .fones(toFoneList(customerDTO.getFones()))

                .build();

    }
    public List<Address> toAddressList(List<AddressDTO> addressDTOS){
        List<Address> addresses = new ArrayList<>();
        for(AddressDTO addressDTO : addressDTOS){
            addresses.add(toAddress(addressDTO));
        }
        return addresses;

    }
    public Address toAddress(AddressDTO addressDTO){
        return Address.builder()
                .street(addressDTO.getStreet())
                .number(addressDTO.getNumber())
                .city(addressDTO.getCity())
                .complement((addressDTO.getComplement()))
                .zipcode(addressDTO.getZipcode())
                .state(addressDTO.getState())

                .build();
    }
    public List<Fone> toFoneList(List<FoneDTO> foneDTOS){
        return foneDTOS.stream().map(this::toFone).toList();

    }
    public Fone toFone(FoneDTO foneDTO){
        return Fone.builder()
                .number(foneDTO.getNumber())
                .ddd(foneDTO.getDdd())
                .build();

    }
    public CustomerDTO toCustomerDTO(Customer customerDTO){
        return CustomerDTO.builder()
                .name(customerDTO.getName())
                .email(customerDTO.getEmail())
                .password(customerDTO.getPassword())
                .addresses(toAddressListDTO(customerDTO.getAddresses()))
                .fones(toFoneListDTO(customerDTO.getFones()))

                .build();

    }
    public List<AddressDTO> toAddressListDTO(List<Address> addressDTOS){
        List<AddressDTO> addresses = new ArrayList<>();
        for(Address addressDTO : addressDTOS){
            addresses.add(toAddressDTO(addressDTO));
        }
        return addresses;

    }
    public AddressDTO toAddressDTO(Address addressDTO){
        return AddressDTO.builder()
                .street(addressDTO.getStreet())
                .number(addressDTO.getNumber())
                .city(addressDTO.getCity())
                .complement((addressDTO.getComplement()))
                .zipcode(addressDTO.getZipcode())
                .state(addressDTO.getState())

                .build();
    }
    public List<FoneDTO> toFoneListDTO(List<Fone> foneDTOS){
        return foneDTOS.stream().map(this::toFoneDTO).toList();

    }
    public FoneDTO toFoneDTO(Fone foneDTO){
        return FoneDTO.builder()
                .number(foneDTO.getNumber())
                .ddd(foneDTO.getDdd())
                .build();

    }
}

