package com.baloch.products.category.service;

import com.baloch.products.category.dto.CategoryRequest;
import com.baloch.products.category.dto.CategoryResponse;
import com.baloch.products.category.model.Category;
import com.baloch.products.category.repository.CategoryRepository;
import com.baloch.products.core.dto.GenericResponseBody;
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



    public Object getCategory(String categoryId){
        Category category = categoryRepository.findById(categoryId).orElse(null);

        if(category == null) {
            GenericResponseBody genericResponseBody= new GenericResponseBody();
            genericResponseBody.setHttpStatus(404);
            genericResponseBody.setMessage("Category with id "+categoryId+" was not found!!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(genericResponseBody);
        }

        CategoryResponse categoryResponse =new CategoryResponse();
        categoryResponse.setCategoryName(category.getCategoryName());
        categoryResponse.setCategoryDesc(category.getCategoryDesc());
        categoryResponse.setKeywords(category.getKeywords());

        GenericResponseBody genericResponseBody= new GenericResponseBody();
        genericResponseBody.setHttpStatus(200);
        genericResponseBody.setMessage("Category with id "+categoryId+" was found successfully");
        genericResponseBody.setBody(categoryResponse);
        return ResponseEntity.status(HttpStatus.OK).body(genericResponseBody);
    }




    public Object getAllCategory(){
        try {
            List<Category> categories = categoryRepository.findAll();
            System.out.println(categories);

            GenericResponseBody genericResponseBody= new GenericResponseBody();
            genericResponseBody.setHttpStatus(200);
            genericResponseBody.setMessage("All Categories found successfully");
            genericResponseBody.setBody(categories);
            return ResponseEntity.status(HttpStatus.OK).body(genericResponseBody);
        }catch (Exception e){
            GenericResponseBody genericResponseBody= new GenericResponseBody();
            genericResponseBody.setHttpStatus(500);
            genericResponseBody.setMessage(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(genericResponseBody);

        }
    }




    public Object createCategory(CategoryRequest categoryRequest){
        Category newCategory = new Category();
        newCategory.setCategoryName(categoryRequest.getCategoryName());
        newCategory.setCategoryDesc(categoryRequest.getCategoryDesc());
        newCategory.setKeywords(categoryRequest.getKeywords());

        try {
            Category category = categoryRepository.save(newCategory);

            GenericResponseBody genericResponseBody = new GenericResponseBody();
            genericResponseBody.setHttpStatus(201);
            genericResponseBody.setMessage("New Category was created successfully");
            genericResponseBody.setBody(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(genericResponseBody);
        }catch (Exception e){
            GenericResponseBody genericResponseBody = new GenericResponseBody();
            genericResponseBody.setHttpStatus(500);
            genericResponseBody.setMessage(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(genericResponseBody);
        }
    }
}
