package com.baloch.products.category.service;

import com.baloch.products.category.dto.CategoryRequest;
import com.baloch.products.category.dto.CategoryResponse;
import com.baloch.products.category.model.Category;
import com.baloch.products.category.repository.CategoryRepository;
import com.baloch.products.core.dto.GenericResponseBody;
import com.baloch.products.core.handlers.HandlerMethod;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServices {
    private CategoryRepository categoryRepository;
    private HandlerMethod handler;



    public Object getCategory(String categoryId){
        Category category = categoryRepository.findById(categoryId).orElse(null);

        if(category == null) {
            GenericResponseBody genericResponseBody= handler.genericResponseBodyMethod(404,
                    "Category with id "+categoryId+" was not found!!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(genericResponseBody);
        }
        CategoryResponse categoryResponse = categoryResponseMethod(category);
        GenericResponseBody genericResponseBody = handler.genericResponseBodyMethod(200,
                "Category with id "+categoryId+" was found successfully", categoryResponse);
        return ResponseEntity.status(HttpStatus.OK).body(genericResponseBody);
    }




    public Object getAllCategory(){
        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponse> categoriesRes = new ArrayList<>();
        try {
            for(Category category : categories){
                CategoryResponse categoryResponse = categoryResponseMethod(category);
                categoriesRes.add(categoryResponse);
            }
            GenericResponseBody genericResponseBody = handler.genericResponseBodyMethod(200,
                    "All Categories found successfully", categoriesRes);
            return ResponseEntity.status(HttpStatus.OK).body(genericResponseBody);
        }catch (Exception e){
            GenericResponseBody genericResponseBody = handler.genericResponseBodyMethod(500,
                    e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(genericResponseBody);
        }
    }




    public Object createCategory(CategoryRequest categoryRequest){
        Category category = requestToCategoryMethod(categoryRequest);
        try {
            Category saveCategory = categoryRepository.save(category);

            CategoryResponse categoryResponse = categoryResponseMethod(saveCategory);
            GenericResponseBody genericResponseBody = handler.genericResponseBodyMethod(201,
                    "New Category was created successfully",
                    categoryResponse);
            return ResponseEntity.status(HttpStatus.CREATED).body(genericResponseBody);
        }catch (Exception e){
            GenericResponseBody genericResponseBody = handler.genericResponseBodyMethod(500,
                    e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(genericResponseBody);
        }
    }

    public Object updateCategory(String categoryId,CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(categoryId).orElse(null);

        if(category==null){
            GenericResponseBody genericResponseBody= handler.genericResponseBodyMethod(404,
                    "Failed to update Category with id "+categoryId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(genericResponseBody);
        }

        if(categoryRequest.getCategoryName() != null){
            category.setCategoryName(categoryRequest.getCategoryName());
        }
        if(categoryRequest.getCategoryDesc() != null){
            category.setCategoryDesc(categoryRequest.getCategoryDesc());
        }
        if(categoryRequest.getKeywords() != null){
            category.setKeywords(categoryRequest.getKeywords());
        }



        try {
            Category saveCategory = categoryRepository.save(category);

            CategoryResponse categoryResponse = categoryResponseMethod(saveCategory);

            GenericResponseBody genericResponseBody = handler.genericResponseBodyMethod(201,
                    "New Category was created successfully",categoryResponse);
            return ResponseEntity.status(HttpStatus.CREATED).body(genericResponseBody);
        }catch (Exception e){
            GenericResponseBody genericResponseBody = handler.genericResponseBodyMethod(500,e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(genericResponseBody);
        }

    }

    // DELETE SERVICE
    public Object deleteCategory(String categoryId){
        categoryRepository.deleteById(categoryId);
        GenericResponseBody genericResponseBody = handler.genericResponseBodyMethod(202,
                "Category with id "+categoryId+" has been deleted successfully");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(genericResponseBody);
    }


    Category requestToCategoryMethod(CategoryRequest categoryRequest){
        Category category = new Category();
        category.setCategoryName(categoryRequest.getCategoryName());
        category.setCategoryDesc(categoryRequest.getCategoryDesc());
        category.setKeywords(categoryRequest.getKeywords());
        return  category;
    }

    CategoryResponse categoryResponseMethod(Category category){
        CategoryResponse categoryResponse =new CategoryResponse();
        categoryResponse.setCategoryName(category.getCategoryName());
        categoryResponse.setCategoryDesc(category.getCategoryDesc());
        categoryResponse.setKeywords(category.getKeywords());
        categoryResponse.setProducts(category.getProducts());
        return categoryResponse;
    }
}
