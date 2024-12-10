package com.adeindra6.catalog.service;

import java.util.List;
import java.util.Map;

import com.adeindra6.catalog.domain.Category;
import com.adeindra6.catalog.dto.CategoryCreateUpdateRecordDTO;
import com.adeindra6.catalog.dto.ResultPageResponseDTO;
import com.adeindra6.catalog.dto.CategoryListResponseDTO;

public interface CategoryService {
    
    public void createAndUpdateRequest(CategoryCreateUpdateRecordDTO dto);

    public ResultPageResponseDTO<CategoryListResponseDTO> findCategoryList(Integer pages, Integer limit, String sortBy, String direction, String categoryName);

    public List<Category> findCategories(List<String> categoryCodeList);

    public List<CategoryListResponseDTO> constructDTO(List<Category> categories);

    public Map<Long, List<String>> findCategoriesMap(List<Long> bookIdList);
}
