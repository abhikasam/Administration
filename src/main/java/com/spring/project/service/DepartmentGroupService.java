package com.spring.project.service;

import com.spring.project.entity.DepartmentGroup;
import com.spring.project.repository.IDepartmentGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentGroupService {
    @Autowired
    private IDepartmentGroupRepository departmentGroupRepository;

    public List<DepartmentGroup> departmentGroups(){
        return departmentGroupRepository.findAll();
    }

}
