package com.quantum.employee.response;
//author : Jere

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class EmployeeResponse {

    private int id;
    private String name;
    private String email;
    private String age;
    private PaymentResponse paymentResponse;
}
