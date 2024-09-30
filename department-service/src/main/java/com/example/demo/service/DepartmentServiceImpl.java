package com.example.demo.service;

import com.example.demo.entity.DepartmentEntity;
import com.example.demo.model.DepartmentPojo;
import com.example.demo.repository.DepartmentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService{

    DepartmentRepository deptRepo;

    public DepartmentServiceImpl(DepartmentRepository deptRepo){
        this.deptRepo=deptRepo;
    }

    @Override
    public List<DepartmentPojo> getAllDepartment() {
        List<DepartmentEntity> allDeptEntity = deptRepo.findAll();
        List<DepartmentPojo> allDeptPojo = new ArrayList<>();
        allDeptEntity.stream().forEach((eachDeptEntity) -> {
            DepartmentPojo deptPojo = new DepartmentPojo();
            BeanUtils.copyProperties(eachDeptEntity,deptPojo);
            allDeptPojo.add(deptPojo);
        });

        return allDeptPojo;
    }

    @Override
    public DepartmentPojo getADepartment(long deptId) {
        Optional<DepartmentEntity> fetchedDeptEntity = deptRepo.findById(deptId);
        DepartmentPojo deptPojo = null;
        if(fetchedDeptEntity.isPresent()){
            deptPojo = new DepartmentPojo();
            BeanUtils.copyProperties(fetchedDeptEntity.get(),deptPojo);
        }
        return deptPojo;
    }

    @Override
    public DepartmentPojo addDepartment(DepartmentPojo newDeptPojo) {
        DepartmentEntity deptEntity = new DepartmentEntity();
        BeanUtils.copyProperties(newDeptPojo,deptEntity);
        deptRepo.saveAndFlush(deptEntity);
        return  newDeptPojo;
    }

    @Override
    public DepartmentPojo updateDepartment(DepartmentPojo editDeptPojo) {
        DepartmentEntity deptEntity = new DepartmentEntity();
        BeanUtils.copyProperties(editDeptPojo,deptEntity);
        deptRepo.saveAndFlush(deptEntity);
        return  editDeptPojo;
    }

    @Override
    public void deleteDepartment(long deptId) {
        deptRepo.deleteById(deptId);
    }
}
