package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DepartmentPojo {

    private long deptId;
    private String deptName;
    private List<EmployeePojo> allEmployees;
}
