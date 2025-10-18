package com.baloch.products.category.dto;

import com.baloch.products.product.model.Product;
import lombok.Data;

import java.util.List;

@Data
public class CategoryResponse {
    private String categoryName;
    private String categoryDesc;
    private List<String> keywords;
    private List<Product> products;
}
