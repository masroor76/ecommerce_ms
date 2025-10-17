package com.baloch.products.controller;

import com.baloch.products.dto.ProductRequest;
import com.baloch.products.dto.ProductResponse;
import com.baloch.products.models.Product;
import com.baloch.products.services.ProductServices;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/product")
public class ProductController {
    private ProductServices productServices;

    @GetMapping("/{productId}")
    public Object getSingleProduct(@PathVariable String productId){
        return productServices.getSingleProducts(productId);
    }

    @GetMapping
    public List<Product> Product(){
        return productServices.getAllProducts();
    }

    @PostMapping
    public Product createProduct(@RequestBody ProductRequest productRequest ){
        return productServices.createProduct(productRequest);
    }
}
