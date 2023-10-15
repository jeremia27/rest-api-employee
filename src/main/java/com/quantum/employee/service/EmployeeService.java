package com.quantum.employee.service;
//author : Jere

import com.quantum.employee.entity.Employee;
import com.quantum.employee.repo.EmployeeRepo;
import com.quantum.employee.response.EmployeeResponse;
import com.quantum.employee.response.PaymentResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RestTemplate restTemplate;


    //GetById
    public EmployeeResponse getEmployeeById(int id) {
        Employee employee = employeeRepo.findEmployeeById(id);
        if (employee != null) {
            EmployeeResponse employeeResponse = mapper.map(employee, EmployeeResponse.class);
            try {
                PaymentResponse paymentResponse = restTemplate.getForObject("http://localhost:6001/payment-service/payment/{id}", PaymentResponse.class, id);
                employeeResponse.setPaymentResponse(paymentResponse);
            } catch (HttpClientErrorException ex) {
                if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                    employeeResponse.setPaymentResponse(new PaymentResponse());
                } else {
                    throw ex;
                }
            }
            return employeeResponse;
        } else {
            throw new IllegalArgumentException("Employee not found with id: " + id);
        }
    }

    //Get All
    public List<EmployeeResponse> getAllEmployees() {
        List<Employee> employees = employeeRepo.getAllEmployees();

        return employees.stream()
                .map(employee -> mapper.map(employee, EmployeeResponse.class))
                .collect(Collectors.toList());
    }

    //Post
    public Employee createEmployee(Employee employee) {
        if (employeeRepo.findEmployeeById(employee.getId()) != null) {
            throw new IllegalArgumentException("Employee with ID " + employee.getId() + " already exists.");
        }

        return employeeRepo.save(employee);
    }

    //Update
    public Employee updateEmployee(int id, Employee updatedEmployee) {
        Employee existingEmployee = employeeRepo.findEmployeeById(id);
        if (existingEmployee != null) {
            existingEmployee.setName(updatedEmployee.getName());
            existingEmployee.setEmail(updatedEmployee.getEmail());
            existingEmployee.setAge(updatedEmployee.getAge());

            return employeeRepo.save(existingEmployee);
        } else {
            throw new IllegalArgumentException("Employee not found with id: " + id);
        }
    }

    //Delete
    public void deleteEmployee(int id) {
        Employee employee = employeeRepo.findEmployeeById(id);
        if (employee != null) {
            employeeRepo.delete(employee);
        } else {
            throw new IllegalArgumentException("Employee not found with id: " + id);
        }
        employeeRepo.deleteEmployeeById(id);
    }
}
