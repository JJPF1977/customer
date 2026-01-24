package com.javanauta.customer.business.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class PhoneDTO {

    private Long id;
    private String number;
    private String ddd;
}
