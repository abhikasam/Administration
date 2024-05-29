package com.spring.project.entity;

import com.spring.project.annotations.DisplayName;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
@Table(name = "departmentgroup")
public class DepartmentGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "groupid")
    @DisplayName("Department Group")
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
