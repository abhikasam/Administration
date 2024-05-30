package com.spring.project.service;

import com.spring.project.entity.Department;
import com.spring.project.repository.IDepartmentRepository;
import com.spring.project.shared.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private IDepartmentRepository departmentRepository;

    public List<Department> departments(){
        return departmentRepository.findAll();
    }

    public List<Department> departments(Pagination pagination){
        var departments=departmentRepository.findAll();
        var totalRecords = departments.size();

        Pagination.updatePagination(totalRecords,pagination);

        var skip = (pagination.getPageNumber() - 1) * pagination.getPageSize();
        var take = pagination.getPageSize();

        return departments.stream().skip(skip).limit(take).toList();
    }

    public Department getDepartment(Integer id){
        if(id!=null){
            var department=departmentRepository.findById(id);
            if(department.isPresent())
                return department.get();
        }
        return new Department();
    }

    public void validate(Department department,boolean isNew,BindingResult bindingResult){
        if(department.getDepartmentCode()==0){
            bindingResult.addError(new FieldError("department","departmentCode","Department Code can't be 0."));
        }
        var allDepartments=departmentRepository.findAll();
        if(isNew && allDepartments.stream().anyMatch(i->i.getDepartmentCode()==department.getDepartmentCode())){
            bindingResult.addError(new FieldError("department","departmentCode","Department Code already taken."));
        }
        if(department.getDepartmentName()!=null &&
                allDepartments.stream().anyMatch(i->
                        i.getDepartmentCode()!=department.getDepartmentCode() &&
                        department.getDepartmentName().toLowerCase().equals(
                                i.getDepartmentName().toLowerCase()))){
            bindingResult.addError(new FieldError("department","departmentName","Department Name already taken."));
        }

        if(department.getEntity().getEntityId()==0){
            bindingResult.addError(new FieldError("department","entity.entityId","Entity must be selected."));
        }
        if(department.getDepartmentGroup().getGroupId()==0){
            bindingResult.addError(new FieldError("department","departmentGroup.groupId","Department Group must be selected"));
        }
    }

    public boolean save(Department department){
        try{
            departmentRepository.save(department);
        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean delete(Department department){
        try{
            departmentRepository.delete(department);
        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}
