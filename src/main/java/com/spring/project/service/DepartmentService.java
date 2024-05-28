package com.spring.project.service;

import com.spring.project.entity.Department;
import com.spring.project.repository.IDepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private IDepartmentRepository departmentRepository;

    public List<Department> departments(){
        return departmentRepository.findAll();
    }

}
