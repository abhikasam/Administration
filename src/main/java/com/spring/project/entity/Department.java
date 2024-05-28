package com.spring.project.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import org.springframework.boot.context.properties.bind.Name;
import org.springframework.web.bind.annotation.ModelAttribute;

@Data
@Entity
@Table(name = "Department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "departmentcode")
    private int departmentCode;
    @Column(name = "name",nullable = false)
    @NotBlank(message = "Department Name is required.")
    @Size(max = 255,message = "Can't exceed 255 letters.")
    private String departmentName;
    @ManyToOne
    @JoinColumn(name = "departmentgroupid")
    private DepartmentGroup departmentGroup;
    @Column(name = "isactive")
    private boolean active;
    @ManyToOne
    @JoinColumn(name = "entityid")
    private com.spring.project.entity.Entity Entity;

    @Transient
    private int entityId;
    @Transient
    private int departmentGroupId;
}
