package com.baloch.products.category.controller;

import com.baloch.products.category.service.CategoryServices;
import com.baloch.products.category.dto.CategoryRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/category")
public class CategoryController {
    private CategoryServices categoryServices;

    @GetMapping("/{categoryId}")
    public Object getCategory(@PathVariable String categoryId){
        return categoryServices.getCategory(categoryId);
    }

    @PostMapping
    public Object saveCategory(@RequestBody CategoryRequest categoryRequest){
        return categoryServices.createCategory(categoryRequest);
    }

    @GetMapping
    public Object getAllCategory(){
        return categoryServices.getAllCategory();
    }

    @PatchMapping("/{categoryId}")
    public Object updateProduct(@PathVariable String categoryId, @RequestBody CategoryRequest categoryRequest){
        return categoryServices.updateCategory(categoryId,categoryRequest);
    }

    @DeleteMapping("/{categoryId}")
    public Object deleteProduct(@PathVariable String categoryId){
        return categoryServices.deleteCategory(categoryId);
    }
}
