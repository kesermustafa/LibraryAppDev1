package com.lib.mapper;


import com.lib.domain.Category;

import com.lib.dto.CategoryDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDTO categoryToCategoryDTO(Category category);


    List<CategoryDTO> map(List<Category> categories);


}
