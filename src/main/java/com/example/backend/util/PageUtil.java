package com.example.backend.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageUtil {

    // Variable
    private int offset = 1;
    private int limit = 10;
    private String sortColumn;
    private Direction direction = Direction.DESC;

    private final static String FORWARD_ORDER = " asc";
    private final static String REVERSE_ORDER = " desc";


    public PageRequest of(PageUtil pageUtil){
        if(pageUtil.getSortColumn() == null || pageUtil.getSortColumn().isEmpty()) return PageRequest.of(pageUtil.getOffset(), pageUtil.limit);
        return PageRequest.of(pageUtil.getOffset(), pageUtil.limit, direction, sortColumn);
    }

    public static String orderBy(String standard, String order){

        if(order != null && !order.isEmpty()){
            return standard + REVERSE_ORDER;
        }
        return order + REVERSE_ORDER;
    }
}
