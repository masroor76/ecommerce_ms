package com.baloch.products.services;

import com.baloch.products.dto.ProductRequest;
import com.baloch.products.models.Product;
import com.baloch.products.repository.CategoryRepository;
import com.baloch.products.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServices {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product createProduct(ProductRequest productRequest) {
        Product newProduct = new Product();
        newProduct.setName(productRequest.getName());
        newProduct.setDescription(productRequest.getDescription());
//        newProduct.setCategory(categoryRepository.findById(productRequest.getCategory()));
        newProduct.setPrice(productRequest.getPrice());
        newProduct.setProductCount(productRequest.getProductCount());
        newProduct.setSQNumber(productRequest.getSQNumber());

        return productRepository.save(newProduct);
    }
}
