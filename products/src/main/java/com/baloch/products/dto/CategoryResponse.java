package com.baloch.products.dto;

import com.baloch.products.models.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class CategoryResponse {
    private String categoryName;
    private String categoryDesc;
    private List<String> keywords;
    private Set<Product> products;
}
