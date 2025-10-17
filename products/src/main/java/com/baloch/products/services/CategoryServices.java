package com.baloch.products.services;

import com.baloch.products.dto.CategoryRequest;
import com.baloch.products.dto.CategoryResponse;
import com.baloch.products.dto.ProductResponse;
import com.baloch.products.models.Category;
import com.baloch.products.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServices {
    private CategoryRepository categoryRepository;

    public Object getCategory(String categoryId){
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if(category == null) {

            return "Product with id "+categoryId+" is not found";
        }
        CategoryResponse categoryResponse =new CategoryResponse();
        categoryResponse.setCategoryName(category.getCategoryName());
        categoryResponse.setCategoryDesc(category.getCategoryDesc());
        categoryResponse.setProducts(category.getProducts());
        categoryResponse.setKeywords(category.getKeywords());
        return categoryResponse;
    }


    public List<Category> getAllCategory(){
        try {
            List<Category> categories = categoryRepository.findAll();
            return categories;
        }catch (Exception e){
            System.out.println(e);
            return null;

        }
    }


    public Category createCategory(CategoryRequest categoryRequest){
        Category newCategory = new Category();
        newCategory.setCategoryName(categoryRequest.getCategoryName());
        newCategory.setCategoryDesc(categoryRequest.getCategoryDesc());
        newCategory.setKeywords(categoryRequest.getKeywords());
        return categoryRepository.save(newCategory);
    }
}
