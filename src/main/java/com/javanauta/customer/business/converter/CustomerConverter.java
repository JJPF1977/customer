package com.javanauta.customer.business.converter;

import com.javanauta.customer.business.dto.AddressDTO;
import com.javanauta.customer.business.dto.CustomerDTO;
import com.javanauta.customer.business.dto.PhoneDTO;
import com.javanauta.customer.infrastructure.entity.Address;
import com.javanauta.customer.infrastructure.entity.Customer;
import com.javanauta.customer.infrastructure.entity.Phone;
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
                .phones(toFoneList(customerDTO.getFones()))

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
    public List<Phone> toFoneList(List<PhoneDTO> phoneDTOS){
        return phoneDTOS.stream().map(this::toFone).toList();

    }
    public Phone toFone(PhoneDTO phoneDTO){
        return Phone.builder()
                .number(phoneDTO.getNumber())
                .ddd(phoneDTO.getDdd())
                .build();

    }
    public CustomerDTO toCustomerDTO(Customer customerDTO){
        return CustomerDTO.builder()
                .name(customerDTO.getName())
                .email(customerDTO.getEmail())
                .password(customerDTO.getPassword())
                .addresses(toAddressListDTO(customerDTO.getAddresses()))
                .fones(toPhoneListDTO(customerDTO.getPhones()))

                .build();

    }
    public List<AddressDTO> toAddressListDTO(List<Address> addressDTOS){
        List<AddressDTO> addresses = new ArrayList<>();
        for(Address addressDTO : addressDTOS){
            addresses.add(toAddressDTO(addressDTO));
        }
        return addresses;

    }
    public AddressDTO toAddressDTO(Address address){
        return AddressDTO.builder()
                .id(address.getId())
                .street(address.getStreet())
                .number(address.getNumber())
                .city(address.getCity())
                .complement((address.getComplement()))
                .zipcode(address.getZipcode())
                .state(address.getState())

                .build();
    }
    public List<PhoneDTO> toPhoneListDTO(List<Phone> phoneDTOS){
        return phoneDTOS.stream().map(this::toPhoneDTO).toList();

    }
    public PhoneDTO toPhoneDTO(Phone phone){
        return PhoneDTO.builder()
                .id(phone.getId())
                .number(phone.getNumber())
                .ddd(phone.getDdd())
                .build();

    }
    public Customer updateCustomer(CustomerDTO customerDTO, Customer entity){
        return Customer.builder()
                .name(customerDTO.getName() != null ? customerDTO.getName() : entity.getName())
                .id(entity.getId())
                .password(customerDTO.getPassword() != null ? customerDTO.getPassword() : entity.getPassword())
                .email(customerDTO.getEmail() != null ? customerDTO.getEmail() : entity.getEmail())
                .addresses(entity.getAddresses())
                .phones(entity.getPhones())
                .build();
    }
    public Address updateAddress(AddressDTO dto, Address entity){
        return Address.builder()
                .id(entity.getId())
                .street(dto.getStreet() != null ? dto.getStreet() : entity.getStreet())
                .number(dto.getNumber() != null ? dto.getNumber() : entity.getNumber())
                .city(dto.getCity() != null ? dto.getCity() : entity.getCity())
                .zipcode(dto.getZipcode() != null ? dto.getZipcode() : entity.getZipcode())
                .complement(dto.getComplement() != null ? dto.getComplement() : entity.getComplement())
                .state(dto.getState() != null ? dto.getState() : entity.getState())
                .build();
    }
    public Phone updatePhone(PhoneDTO dto, Phone entity){
        return Phone.builder()
                .id(entity.getId())
                .ddd(dto.getDdd() != null ? dto.getDdd() : entity.getDdd())
                .number(dto.getNumber() != null ? dto.getNumber() : entity.getNumber())
                .build();
    }
}

