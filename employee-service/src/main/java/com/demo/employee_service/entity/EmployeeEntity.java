package com.demo.employee_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Table(name = "employee_details")
@Entity
public class EmployeeEntity {

    @Id
    @Column(name = "employee_id")
    private long employeeId;

    @Column(name = "employee_name")
    private String name;

    @Column(name = "dept_id")
    private long deptId;

}
