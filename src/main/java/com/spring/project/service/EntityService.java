package com.spring.project.service;

import com.spring.project.entity.Entity;
import com.spring.project.repository.IEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntityService {
    @Autowired
    private IEntityRepository entityRepository;

    public List<Entity> entites(){
        return entityRepository.findAll();
    }
}
