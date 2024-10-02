package com.example.demo.controller;

import com.example.demo.model.DepartmentPojo;
import com.example.demo.model.EmployeePojo;
import com.example.demo.service.DepartmentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DepartmentController {

    DepartmentService deptService;

    public static final Logger LOG = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    public DepartmentController(DepartmentService deptService){
        this.deptService = deptService;
    }

    @GetMapping("/admin/departments")
    public List<DepartmentPojo> getAllDepartments(){
        LOG.info("in getAllDepartments()");
        return deptService.getAllDepartment();
    }

    @GetMapping("/user/departments/{id}")
    @CircuitBreaker(name = "ciremp", fallbackMethod = "empFallBack")
    public DepartmentPojo getADepartment(@PathVariable("id") long deptId){
        LOG.info("in getADepartments()");
        DepartmentPojo deptPojo = deptService.getADepartment(deptId);
        RestClient restClient = RestClient.create();
        List<EmployeePojo> allEmps = restClient.get().uri("http://localhost:8082/api/employees/department/"+deptId).retrieve().body(List.class);
        deptPojo.setAllEmployees(allEmps);
        return deptPojo;
    }

    public DepartmentPojo empFallBack(){
        return new DepartmentPojo(0,"fallback",null);
    }

    @PostMapping("/departments")
    public DepartmentPojo addDepartment(@RequestBody DepartmentPojo newDept){
        LOG.info("in addDepartments()");
        return deptService.addDepartment(newDept);
    }

    @PutMapping("/departments")
    public DepartmentPojo updateDepartment(@RequestBody DepartmentPojo editDept){
        LOG.info("in editDepartments()");
        return deptService.updateDepartment(editDept);
    }

    @DeleteMapping("/departments/{id}")
    public void removeDept(@PathVariable("id") long deptId){
        LOG.info("in removeDept()");
        deptService.deleteDepartment(deptId);
    }

}
