package com.spring.project.repository;

import com.spring.project.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface IDepartmentRepository extends JpaRepository<Department,Integer> {
}
