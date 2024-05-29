package com.spring.project.repository;

import com.spring.project.entity.DepartmentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDepartmentGroupRepository extends JpaRepository<DepartmentGroup,Integer> {
}
