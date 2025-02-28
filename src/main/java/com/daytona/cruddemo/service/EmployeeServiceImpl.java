package com.daytona.cruddemo.service;

import com.daytona.cruddemo.dao.EmployeeDao;
import com.daytona.cruddemo.entity.Employee;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDao employeedao;

    @Override
    public List<Employee> findAll() {
        return employeedao.findAll();
    }

    @Override
    public Employee findById(Long theId) {
        return employeedao.findById(theId);
    }

    @Override
    @Transactional
    public Employee save(Employee theEmployee) {
        return employeedao.save(theEmployee);
    }

    @Override
    @Transactional
    public void deleteById(Long theId) {
        employeedao.deleteById(theId);
    }
}
