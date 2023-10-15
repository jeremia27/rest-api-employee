package com.quantum.employee.repo;
//author : Jere

import com.quantum.employee.entity.Employee;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepo extends CrudRepository<Employee, Integer> {

    @Query(value = "SELECT * FROM employee WHERE id = :id", nativeQuery = true)
    Employee findEmployeeById(@Param("id") int id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE employee SET name = :name, email = :email, age = :age WHERE id = :id", nativeQuery = true)
    void updateEmployee(@Param("id") int id, @Param("name") String name, @Param("email") String email, @Param("age") String age);


    @Query(value = "SELECT * FROM employee", nativeQuery = true)
    List<Employee> getAllEmployees();


    @Transactional
    @Modifying
    @Query(value = "DELETE FROM employee WHERE id = :id", nativeQuery = true)
    void deleteEmployeeById(@Param("id") int id);
}
