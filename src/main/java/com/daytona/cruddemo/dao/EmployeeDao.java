package com.daytona.cruddemo.dao;

import com.daytona.cruddemo.entity.Employee;

import java.util.List;

public interface EmployeeDao {
    List<Employee> findAll();

    Employee findById(Long theId);

    Employee save(Employee theEmployee);

    void deleteById(Long theId);

}
