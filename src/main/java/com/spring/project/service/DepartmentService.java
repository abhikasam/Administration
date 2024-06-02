package com.spring.project.service;

import com.spring.project.entity.Department;
import com.spring.project.filter.DepartmentFilter;
import com.spring.project.repository.IDepartmentRepository;
import com.spring.project.shared.Pagination;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.hibernate.query.criteria.JpaSubQuery;
import org.hibernate.query.sqm.tree.SqmCopyContext;
import org.hibernate.query.sqm.tree.select.SqmQuerySpec;
import org.hibernate.query.sqm.tree.select.SqmSelectStatement;
import org.hibernate.query.sqm.tree.select.SqmSubQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class DepartmentService {
    @Autowired
    private IDepartmentRepository departmentRepository;

    @Autowired
    private EntityManager entityManager;
    public List<Department> departments(){
        return departmentRepository.findAll();
    }

    public List<Department> departments(Pagination pagination, DepartmentFilter departmentFilter) {
        var totalRecords= totalRecords(departmentFilter);
        var totalRecordCount = totalRecordCount(departmentFilter);
        Pagination.updatePagination(totalRecordCount,pagination);

        var skip = (pagination.getPageNumber() - 1) * pagination.getPageSize();
        var take = pagination.getPageSize();

        return totalRecords
                .setFirstResult(skip).setMaxResults(take).getResultList();
    }

    private Predicate[] getPredicates(CriteriaBuilder builder,Root<Department> root,DepartmentFilter departmentFilter){
        List<Predicate> predicates=new ArrayList<>();
        if(departmentFilter!=null){
            root.join("entity");
            root.join("departmentGroup");
            if (departmentFilter.getDepartmentName() != null && !departmentFilter.getDepartmentName().isEmpty()) {
                predicates.add(
                        (Predicate) builder.and(
                                builder.isNotNull(root.get("departmentName")),
                                builder.like(
                                        root.get("departmentName"),
                                        "%" + departmentFilter.getDepartmentName() + "%"
                                )
                        )
                );
            }
            if (departmentFilter.getEntityId() != 0) {
                predicates.add((Predicate)
                        builder.equal(
                                root.get("entity").get("entityId"),
                                departmentFilter.getEntityId()
                        )
                );
            }
            if(departmentFilter.getGroupId()!=0){
                predicates.add((Predicate)
                        builder.equal(
                                root.get("departmentGroup").get("groupId"),
                                departmentFilter.getGroupId()
                        )
                );
            }
        }
        return predicates.toArray(new Predicate[0]);
    }

    private TypedQuery<Department> totalRecords(DepartmentFilter departmentFilter){
        var builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Department> query = builder.createQuery(Department.class);
        Root<Department> root = query.from(Department.class);
        Predicate[] predicates=getPredicates(builder,root,departmentFilter);

        query.where(predicates);
        query.select(root);
        return entityManager.createQuery(query);
    }
    private Long totalRecordCount(DepartmentFilter departmentFilter){
        CriteriaBuilder builder=entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        Root<Department> countRoot=countQuery.from(Department.class);
        Predicate[] predicates=getPredicates(builder,countRoot,departmentFilter);
        countQuery.where(predicates);
        countQuery.select(builder.count(countRoot));
        return entityManager.createQuery(countQuery).getSingleResult();
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
