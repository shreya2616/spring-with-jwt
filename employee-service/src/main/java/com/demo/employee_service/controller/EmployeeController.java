package com.demo.employee_service.controller;

import com.demo.employee_service.entity.EmployeeEntity;
import com.demo.employee_service.model.EmployeePojo;
import com.demo.employee_service.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    public EmployeeService employeeService;

    public static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<EmployeePojo> getAllEmployee(){
        LOG.info("in getAllEmployee()");
        return employeeService.getAllEmployee();
    }

    @GetMapping("/employees/{id}")
    public EmployeePojo getAEmployee(@PathVariable("id") long employeeId){
        LOG.info("in getAEmployee()");
        return employeeService.getAEmployee(employeeId);
    }

    @PostMapping("/employees")
    public EmployeePojo addEmployee(@RequestBody EmployeePojo newEmployee){
        LOG.info("in addEmployee()");
        return employeeService.addEmployee(newEmployee);
    }

    @PutMapping("/employees")
    public EmployeePojo updateEmployee(@RequestBody EmployeePojo editEmployee){
        LOG.info("in updateEmployee()");
        return employeeService.updateEmployee(editEmployee);
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployee(@PathVariable("id") long employeeId){
        LOG.info("in deleteEmployee()");
        employeeService.deleteEmployee(employeeId);
    }

    @GetMapping("/employees/department/{id}")
    public List<EmployeePojo> getByDept(@PathVariable("id") long id){
        LOG.info("in getByDept()");
        return employeeService.getAllEmployeeByDepartment(id);
    }
}
