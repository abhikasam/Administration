package com.spring.project.repository;

import jakarta.persistence.*;

@Entity
@Table(name = "departmentgroup")
public class DepartmentGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "groupid")
    private int groupId;
    @Column(name = "name")
    private String name;
    @Column(name = "isactive")
    private boolean isActive;
    @Column(name = "grouptype")
    private char groupType;
    @Column(name = "userexpertisegroupid")
    private int userExpertiseGroupId;
    @Column(name = "displayinreferral")
    private boolean displayInReferral;
}
