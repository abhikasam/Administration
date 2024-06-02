package com.spring.project.shared;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "pagination.default")
public class Pagination {
    private int pageNumber=1;
    private int pageSize=10;
    private long totalPages=1;

    public Pagination(){}
    public Pagination(Integer pageNumber,Integer pageSize){
        if(pageNumber!=null)
            this.pageNumber=pageNumber;
        if(pageSize!=null)
            this.pageSize=pageSize;
    }

    public static void updatePagination(long totalRecords,Pagination pagination){
        var totalPages = (totalRecords + pagination.pageSize) / pagination.pageSize;

        if (totalRecords % pagination.pageSize == 0)
        {
            totalPages--;
        }

        if (totalPages < pagination.pageNumber)
        {
            pagination.pageNumber=1;
        }
        pagination.totalPages=totalPages;
    }
}
