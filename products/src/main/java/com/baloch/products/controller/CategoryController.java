package com.baloch.products.controller;

import com.baloch.products.dto.CategoryRequest;
import com.baloch.products.models.Category;
import com.baloch.products.services.CategoryServices;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/category")
public class CategoryController {
    private CategoryServices categoryServices;

    @GetMapping("/{categoryId}")
    public Optional<Category> getCategory(@PathVariable String categoryId){
        return categoryServices.getCategory(categoryId);
    }

    @PostMapping
    public Category saveCategory(@RequestBody CategoryRequest categoryRequest){
        return categoryServices.createCategory(categoryRequest);
    }

    @GetMapping
    public List<Category> getAllCategory(){
        return categoryServices.getAllCategory();
    }
}
