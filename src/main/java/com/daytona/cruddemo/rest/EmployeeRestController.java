package com.daytona.cruddemo.rest;

import com.daytona.cruddemo.entity.Employee;
import com.daytona.cruddemo.service.EmployeeService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeRestController {
    private EmployeeService employeeService;

    public EmployeeRestController(EmployeeService theEmployeeService) {
        employeeService = theEmployeeService;
    }

    @GetMapping
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    @GetMapping("/{employeeId}")
    public Employee getEmployee(@PathVariable Long employeeId) {
        Employee theEmployee = employeeService.findById(employeeId);
        if (theEmployee == null) {
            throw new RuntimeException("Employee id not found - " + employeeId);
        }
        return theEmployee;
    }

    @PostMapping
    public Employee addEmployee(@RequestBody Employee theEmployee) {
        theEmployee.setId(0L);
        Employee dbEmployee = employeeService.save(theEmployee);
        return dbEmployee;
    }

    @PutMapping
    public Employee updateEmployee(@RequestBody Employee theEmployee) {
        Employee dbEmployee = employeeService.save(theEmployee);
        return dbEmployee;
    }


    @DeleteMapping("/{employeeId}")
    public String deleteEmployee(@PathVariable Long employeeId) {
//        Employee tempEmployee = employeeService.findById(employeeId);
//        if (tempEmployee == null) {
//            throw new RuntimeException("EmployeeId not found - " + employeeId);
//        }
        employeeService.deleteById(employeeId);
        return "Deleted employee id" + employeeId;
    }
}
