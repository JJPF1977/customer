package com.javanauta.customer.business.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDTO {

    private String name;
    private String email;
    private String password;
    private List<AddressDTO> addresses;
    private List<FoneDTO> fones;
}
