package com.baloch.products.services;

import com.baloch.products.dto.CategoryRequest;
import com.baloch.products.models.Category;
import com.baloch.products.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServices {
    private CategoryRepository categoryRepository;

    public Optional<Category> getCategory(String categoryId){
        return categoryRepository.findById(categoryId);
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
