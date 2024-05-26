package com.spring.project.repository;

import jakarta.persistence.*;

@Entity
@Table(name = "Department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "departmentcode")
    private int departmentCode;
    @Column(name = "name")
    private String name;
    @Column(name = "groupid")
    private int groupId;
    @Column(name = "isactive")
    private boolean isActive;
    @Column(name = "entityid")
    private int entityId;
}
