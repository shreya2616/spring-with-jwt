package com.example.demo.service;

import com.example.demo.model.DepartmentPojo;
import com.example.demo.repository.DepartmentRepository;

import java.util.List;

public interface DepartmentService {
    List<DepartmentPojo> getAllDepartment();

    DepartmentPojo getADepartment(long deptId);

    DepartmentPojo addDepartment(DepartmentPojo newDeptPojo);

    DepartmentPojo updateDepartment(DepartmentPojo editDeptPojo);

    void deleteDepartment(long deptId);

}
