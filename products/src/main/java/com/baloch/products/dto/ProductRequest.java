package com.baloch.products.dto;

import com.baloch.products.models.Category;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class ProductRequest {
    private  String name;
    private String description;
    private String category;
    private int productCount;
    private String SQNumber;
    private float price;
}
