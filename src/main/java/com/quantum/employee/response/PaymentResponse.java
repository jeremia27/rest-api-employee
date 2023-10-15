package com.quantum.employee.response;
//author : Jere

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PaymentResponse {

    private String bank;
    private String city;
    private String state;
}
