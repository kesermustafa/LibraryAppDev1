package com.lib.service;

import com.lib.domain.Category;
import com.lib.dto.CategoryDTO;
import com.lib.dto.request.CategoryRequest;
import com.lib.exception.ResourceNotFoundException;
import com.lib.exception.message.ErrorMessage;
import com.lib.mapper.CategoryMapper;
import com.lib.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }


    public void categorySave(CategoryRequest categoryRequest) {

        Category category = new Category();
        List<Category> categoryList = categoryRepository.findAll();

        if(categoryList.size()==0){
            category.setSequence(1);
        }

        category.setName(categoryRequest.getName());
        category.setBuiltIn(categoryRequest.isBuiltIn());
        category.setSequence(endSequence().get(0).getSequence()+1);

        categoryRepository.save(category);

    }


    public CategoryDTO getCateoryById(Long id) {

        CategoryDTO categoryDTO = new CategoryDTO();

        Category category = findById(id);

        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setBuiltIn(category.isBuiltIn());
        categoryDTO.setSequence(category.getSequence());

        return categoryDTO;
    }



    public Category findById(Long id){

        return categoryRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.CATEGORY_NOT_FOUNT_EXCEPTION, id)));
    }


    public void deleteCategory(Long id) {

        Category category = findById(id);

        categoryRepository.delete(category);
    }


    public void updateCategory(Long id, CategoryRequest categoryRequest) {

        Category category = findById(id);

        category.setName(categoryRequest.getName());
        category.setBuiltIn(categoryRequest.isBuiltIn());
        category.setSequence(categoryRequest.getSequence());

        categoryRepository.save(category);

    }


    public List<Category> endSequence(){
        return categoryRepository.findBySequence();
    }


    public Page<CategoryDTO> findAllWithPage(Pageable pageable) {

        Page<Category> categoryPage = categoryRepository.findAll(pageable);

        Page<CategoryDTO> categoryDTOS =
                categoryPage.map(category->categoryMapper.categoryToCategoryDTO(category));

        return categoryDTOS;
    }









}
