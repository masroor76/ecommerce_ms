package com.baloch.products.product.service;

import com.baloch.products.core.dto.GenericResponseBody;
import com.baloch.products.category.model.Category;
import com.baloch.products.category.repository.CategoryRepository;
import com.baloch.products.product.repository.ProductRepository;
import com.baloch.products.product.dto.ProductRequest;
import com.baloch.products.product.dto.ProductResponse;
import com.baloch.products.product.model.Product;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductServices {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;



    public Object getSingleProducts(String productId) {
        Product product = productRepository.findById(productId).orElse(null);

        if(product == null) {
            GenericResponseBody genericResponseBody= new GenericResponseBody();
            genericResponseBody.setHttpStatus(404);
            genericResponseBody.setMessage("Product with id "+productId+" was not found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(genericResponseBody);
        }

        ProductResponse productResponse = new ProductResponse();
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setProductCount(product.getProductCount());

        GenericResponseBody genericResponseBody= new GenericResponseBody();
        genericResponseBody.setHttpStatus(200);
        genericResponseBody.setMessage("Product with id "+productId+" was found successfully");
        genericResponseBody.setBody(productResponse);

        return ResponseEntity.status(HttpStatus.OK).body(genericResponseBody);
    }



    public Object getAllProducts(){
        try {
            List<Product> plist = productRepository.findAll();

            GenericResponseBody genericResponseBody = new GenericResponseBody();
            genericResponseBody.setHttpStatus(200);
            genericResponseBody.setMessage("All Products found successfully");
            genericResponseBody.setBody(plist);

            return ResponseEntity.status(HttpStatus.OK).body(genericResponseBody);
        }catch (Exception e){
        GenericResponseBody genericResponseBody= new GenericResponseBody();
        genericResponseBody.setHttpStatus(500);
        genericResponseBody.setMessage(e.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(genericResponseBody);

    }
    }




    public Object createProduct(ProductRequest productRequest) {
        Product newProduct = new Product();
        newProduct.setName(productRequest.getName());
        newProduct.setDescription(productRequest.getDescription());
        newProduct.setPrice(productRequest.getPrice());
        newProduct.setProductCount(productRequest.getProductCount());
        newProduct.setSQNumber(productRequest.getSQNumber());
        Category category = categoryRepository.findById(productRequest.getCategory()).orElse(null);
        newProduct.setCategory(category);

        try {
            Product saveProduct = productRepository.save(newProduct);

            ProductResponse productResponse = new ProductResponse();
            productResponse.setName(saveProduct.getName());
            productResponse.setDescription(saveProduct.getDescription());
            productResponse.setPrice(saveProduct.getPrice());
            productResponse.setPrice(saveProduct.getPrice());
            productResponse.setCategory(saveProduct.getCategory());


            GenericResponseBody genericResponseBody = new GenericResponseBody();
            genericResponseBody.setHttpStatus(201);
            genericResponseBody.setMessage("Product created successfully");
            genericResponseBody.setBody(productResponse);

            return ResponseEntity.status(HttpStatus.CREATED).body(genericResponseBody);
        }catch (Exception e){
            GenericResponseBody genericResponseBody = new GenericResponseBody();
            genericResponseBody.setHttpStatus(500);
            genericResponseBody.setMessage(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(genericResponseBody);
        }
    }
}
