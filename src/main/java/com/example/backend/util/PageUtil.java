package com.example.backend.util;

import lombok.Data;

@Data
public class PageUtil {

    // Variable
    private int pageNum;
    private int pageSize;

    private final static String FORWARD_ORDER = " asc";
    private final static String REVERSE_ORDER = " desc";

    public PageUtil(){
        this.pageNum = 1;
        this.pageSize = 10;
    }

    public static String orderBy(String standard, String order){

        if(order != null && !order.isEmpty()){
            return standard + REVERSE_ORDER;
        }
        return order + REVERSE_ORDER;
    }
}
