package com.spring.project.entity;

import com.spring.project.annotations.DisplayName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@jakarta.persistence.Entity
@Table(name = "entity")
@NoArgsConstructor
public class Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @DisplayName("Entity")
    private int entityId;
    @Column(name = "name",nullable = false,length = 200)
    @DisplayName("Entity")
    private String entityName;
    @Column(name = "isactive")
    private boolean isActive;

    @Column(name = "expensecorporateentity",length = 20)
    private String expenseCorporateEntity;

    @Column(name = "hrchangenotificationemail",length = 500)
    private String hrChangeNotificationEmail;

    @Column(name = "expensecorporateentityname")
    private String expenseCorporateEntityName;

    @Column(name = "d3chartcolorcode",length = 7)
    private String d3chartColorCode;

    @Column(name = "shortname",length = 20)
    private String shortName;
    @Column(name = "hrsupport")
    private Integer hrSupportId;
    @Column(name = "hrowner")
    private Integer hrOwnerId;



}
