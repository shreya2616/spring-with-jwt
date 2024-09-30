package com.demo.employee_service.repository;

import com.demo.employee_service.entity.EmployeeEntity;
import com.demo.employee_service.model.EmployeePojo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Long> {
    List<EmployeeEntity> findByDeptId(long deptId);
}
