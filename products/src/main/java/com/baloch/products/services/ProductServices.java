package com.baloch.products.services;

import com.baloch.products.dto.ProductRequest;
import com.baloch.products.dto.ProductResponse;
import com.baloch.products.exceptions.CustomException;
import com.baloch.products.models.Product;
import com.baloch.products.repository.ProductRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.hibernate.mapping.Map;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServices {
    private ProductRepository productRepository;



    public Object getSingleProducts(String productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if(product == null) {

            return "Product with id "+productId+" is not found";
        }
        ProductResponse productResponse =new ProductResponse();
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setProductCount(product.getProductCount());
        return productResponse;
    }



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
