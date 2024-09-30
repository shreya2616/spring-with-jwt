package com.demo.employee_service.service;

import com.demo.employee_service.entity.EmployeeEntity;
import com.demo.employee_service.model.EmployeePojo;
import com.demo.employee_service.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    public EmployeeRepository employeeRepo;

    @Override
    public List<EmployeePojo> getAllEmployee() {
        List<EmployeeEntity> allEmployeeEntity = employeeRepo.findAll();
        List<EmployeePojo> allEmployeePojo = new ArrayList<>();
        allEmployeeEntity.stream().forEach((eachEmployeeEntity) -> {
            EmployeePojo employeePojo = new EmployeePojo();
            BeanUtils.copyProperties(eachEmployeeEntity,employeePojo);
            allEmployeePojo.add(employeePojo);
        });

        return allEmployeePojo;
    }

//    @Override
//    public List<EmployeePojo> findEmpByDept(long deptId) {
//        List<EmployeePojo> allEmployeeEntity = employeeRepo.findByEmpDeptId(deptId);
//        return allEmployeeEntity;
//    }

    @Override
    public EmployeePojo getAEmployee(long employeeId) {
        Optional<EmployeeEntity> fetchedEmployeeEntity = employeeRepo.findById(employeeId);
        EmployeePojo employeePojo = null;
        if(fetchedEmployeeEntity.isPresent()){
            employeePojo = new EmployeePojo();
            BeanUtils.copyProperties(fetchedEmployeeEntity.get(),employeePojo);
        }
        return employeePojo;
    }

    @Override
    public EmployeePojo addEmployee(EmployeePojo newEmployee) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        BeanUtils.copyProperties(newEmployee,employeeEntity);
        employeeRepo.saveAndFlush(employeeEntity);
        return  newEmployee;
    }

    @Override
    public EmployeePojo updateEmployee(EmployeePojo editEmployee) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        BeanUtils.copyProperties(editEmployee,employeeEntity);
        employeeRepo.saveAndFlush(employeeEntity);
        return  editEmployee;
    }

    @Override
    public void deleteEmployee(long employeeId) {
        employeeRepo.deleteById(employeeId);
    }

    @Override
    public List<EmployeePojo> getAllEmployeeByDepartment(long deptId) {
        List<EmployeeEntity> getEmpEntity = employeeRepo.findByDeptId(deptId);
        List<EmployeePojo> allEmpPojo = new ArrayList<>();
        getEmpEntity.stream().forEach((eachEmpEntity)->{
            EmployeePojo empPojo = new EmployeePojo();
            BeanUtils.copyProperties(eachEmpEntity,empPojo);
            allEmpPojo.add(empPojo);
        });
        return allEmpPojo;
    }


}
