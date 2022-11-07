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
    private String direction = "desc";

    public PageRequest of(PageUtil pageUtil){
        if(pageUtil.getSortColumn() == null || pageUtil.getSortColumn().isEmpty()) return PageRequest.of(pageUtil.getOffset(), pageUtil.limit);
        return PageRequest.of(pageUtil.getOffset(), pageUtil.limit, direction.equals("asc") ? Direction.ASC : Direction.DESC, sortColumn);
    }


}
