package com.daytona.cruddemo.service;

import com.daytona.cruddemo.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();

    Employee findById(Long theId);

    Employee save(Employee theEmployee);

    void deleteById(Long theId);
}
