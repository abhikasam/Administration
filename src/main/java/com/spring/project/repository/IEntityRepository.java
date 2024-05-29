package com.spring.project.repository;

import com.spring.project.entity.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEntityRepository extends JpaRepository<Entity,Integer> {
}
