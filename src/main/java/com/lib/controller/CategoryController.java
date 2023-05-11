package com.lib.controller;

import com.lib.domain.Category;
import com.lib.dto.CategoryDTO;
import com.lib.dto.PublisherDTO;
import com.lib.dto.request.CategoryRequest;
import com.lib.dto.response.LibResponse;
import com.lib.dto.response.ResponseMessage;
import com.lib.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LibResponse> categorySave(@Valid @RequestBody CategoryRequest categoryRequest){

        categoryService.categorySave(categoryRequest);

        LibResponse response = new LibResponse(ResponseMessage.CATEGORY_CREATED_RESPONSE_MESSAGE, true);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }


    @GetMapping("/visitors/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("id") Long id){

        CategoryDTO categoryDTO = categoryService.getCateoryById(id);

        return ResponseEntity.ok(categoryDTO);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LibResponse> deleteCategory(@PathVariable Long id){

        categoryService.deleteCategory(id);

        LibResponse response = new LibResponse(ResponseMessage.CATEGORY_DELETE_RESPONSE_MESSAGE, true);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LibResponse> updateCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest){

        categoryService.updateCategory(id, categoryRequest);

        LibResponse response = new LibResponse(ResponseMessage.CATEGORY_UPDATE_RESPONSE_MESSAGE, true);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }



    @GetMapping("/visitors")
    public ResponseEntity<Page<CategoryDTO>> getAllAuthorWithPage(@RequestParam("page") int page,
                                                                  @RequestParam("size") int size,
                                                                  @RequestParam("sort") String prop,
            @RequestParam(value = "directon",required = false,defaultValue = "ASC")  Sort.Direction direction) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));

        Page<CategoryDTO> categoryDTOS = categoryService.findAllWithPage(pageable);
        return ResponseEntity.ok(categoryDTOS);
    }






}
