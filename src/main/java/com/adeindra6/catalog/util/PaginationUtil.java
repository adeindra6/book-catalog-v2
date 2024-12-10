package com.adeindra6.catalog.util;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.adeindra6.catalog.dto.ResultPageResponseDTO;

public class PaginationUtil {

    public static <T> ResultPageResponseDTO createResultPageDTO(List<T> dtos, Long totalElements, Integer pages) {
        ResultPageResponseDTO<T> result = new ResultPageResponseDTO<T>();
        result.setPages(pages);
        result.setElements(totalElements);
        result.setResult(dtos);

        return result;
    }
    
    public static Sort.Direction getSortBy(String sortBy) {
        if(sortBy.equalsIgnoreCase("asc")) {
            return Sort.Direction.ASC;
        }
        else {
            return Sort.Direction.DESC;
        }
    }
}
