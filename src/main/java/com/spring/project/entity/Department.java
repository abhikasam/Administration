package com.spring.project.entity;

import com.spring.project.annotations.DisplayName;
import com.spring.project.shared.PersistanceValidationGroup;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import lombok.Data;

import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.web.bind.annotation.ModelAttribute;

@Data
@Entity
@Table(name = "Department")
public class Department {
    @Id
    @Column(name = "departmentcode")
    @DisplayName("Department Code")
    private int departmentCode;

    @Column(name = "name",nullable = false)
    @NotBlank(message = "Department Name is required.")
    @Size(max = 100,message = "Can't exceed 100 letters.")
    @DisplayName("Department Name")
    private String departmentName;

    @ManyToOne
    @JoinColumn(name = "groupid")
    private DepartmentGroup departmentGroup;

    @Column(name = "isactive")
    @DisplayName("Active")
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "entityid")
    private com.spring.project.entity.Entity entity;

    @Transient
    @DisplayName("Entity")
    private int entityId=0;

    @Transient
    @DisplayName("Department Group")
    private int departmentGroupId=0;

    public void setEntityId(int id){
        this.entity=new com.spring.project.entity.Entity();
        entity.setEntityId(id);
        this.entityId=id;
    }
    public int getEntityId(){
        return this.entityId;
    }
    public void setDepartmentGroupId(int id){
        this.departmentGroup=new DepartmentGroup();
        departmentGroup.setGroupId(id);
        this.departmentGroupId=id;
    }

    public int getDepartmentGroupId(){
        return this.departmentGroupId;
    }
}
