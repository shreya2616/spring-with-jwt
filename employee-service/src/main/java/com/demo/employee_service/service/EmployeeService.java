package com.demo.employee_service.service;

import com.demo.employee_service.entity.EmployeeEntity;
import com.demo.employee_service.model.EmployeePojo;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<EmployeePojo> getAllEmployee();
    EmployeePojo getAEmployee(long employeeId);
    EmployeePojo addEmployee(EmployeePojo newEmployee);
    EmployeePojo updateEmployee(EmployeePojo editEmployee);
    void deleteEmployee(long employeeId);
    List<EmployeePojo> getAllEmployeeByDepartment(long deptId);
}
