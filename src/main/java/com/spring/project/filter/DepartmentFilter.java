package com.spring.project.filter;

import com.spring.project.annotations.DisplayName;
import lombok.Data;

@Data
public class DepartmentFilter {
    @DisplayName("Department Name")
    private String departmentName;
    @DisplayName("Entity")
    private int entityId;
    @DisplayName("Department Group")
    private int groupId;
}
