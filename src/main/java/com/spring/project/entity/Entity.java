package com.spring.project.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@jakarta.persistence.Entity
@Table(name = "entity")
public class Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int entityId;
    @Column(name = "name",nullable = false,length = 200)
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
