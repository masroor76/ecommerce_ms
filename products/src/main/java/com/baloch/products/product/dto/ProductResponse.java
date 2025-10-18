package com.baloch.products.product.dto;

import com.baloch.products.category.model.Category;
import lombok.*;

@Data
public class ProductResponse {

    private String name;

    private String description;

    private int productCount;

    private float price;

    private String category;

}
