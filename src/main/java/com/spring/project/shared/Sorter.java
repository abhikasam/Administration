package com.spring.project.shared;

import com.spring.project.enums.SortingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sorter {
    @Autowired
    private String sortBy;
    @Autowired
    private SortingType orderBy=SortingType.ASCENDING;
}

