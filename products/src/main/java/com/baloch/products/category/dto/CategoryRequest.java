package com.baloch.products.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class CategoryRequest {
    private String categoryName;
    private String categoryDesc;
    private List<String> keywords;
}