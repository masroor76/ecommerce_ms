package com.baloch.products.product.service;

import com.baloch.products.core.dto.GenericResponseBody;
import com.baloch.products.category.model.Category;
import com.baloch.products.category.repository.CategoryRepository;
import com.baloch.products.core.handlers.HandlerMethod;
import com.baloch.products.product.repository.ProductRepository;
import com.baloch.products.product.dto.ProductRequest;
import com.baloch.products.product.dto.ProductResponse;
import com.baloch.products.product.model.Product;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductServices {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private HandlerMethod handler;
    private KafkaTemplate<String, Product> kafkaTemplate;


    // SINGLE PRODUCT SERVICE
    public Object getSingleProducts(String productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if(product == null) {
            GenericResponseBody genericResponseBody= handler.genericResponseBodyMethod(404,
                    "Product with id "+productId+" was not found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(genericResponseBody);
        }
        kafkaTemplate.send("product",product);
        ProductResponse productResponse = productResponseMethod(product);
        GenericResponseBody genericResponseBody=  handler.genericResponseBodyMethod(200,
                "Product with id "+productId+" was found successfully",
                productResponse);
        return ResponseEntity.status(HttpStatus.OK).body(genericResponseBody);
    }


    // ALL PRODUCTS SERVICE
    public Object getAllProducts(){
        try {
            List<Product> plist = productRepository.findAll();
            List<ProductResponse> productResponses = new ArrayList<>();
            for(Product product : plist){
                ProductResponse productResponse = productResponseMethod(product);
                productResponses.add(productResponse);
            }
            GenericResponseBody genericResponseBody = handler.genericResponseBodyMethod(200,"All Products found successfully",productResponses);
            return ResponseEntity.status(HttpStatus.OK).body(genericResponseBody);
        }catch (Exception e){
            GenericResponseBody genericResponseBody = handler.genericResponseBodyMethod(500,e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(genericResponseBody);
        }
    }



    // CREATE SERVICE
    public Object createProduct(ProductRequest productRequest) {
        Product newProduct = newProductMethod(productRequest);
        Category category = categoryRepository.findById(productRequest.getCategory()).orElse(null);
        newProduct.setCategory(category);
        try {
            Product saveProduct = productRepository.save(newProduct);
            ProductResponse productResponse = productResponseMethod(saveProduct);
            GenericResponseBody genericResponseBody = handler.genericResponseBodyMethod(201,
                    "Product created successfully",productResponse);
            return ResponseEntity.status(HttpStatus.CREATED).body(genericResponseBody);
        }catch (Exception e){
            GenericResponseBody genericResponseBody = handler.genericResponseBodyMethod(500,e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(genericResponseBody);
        }
    }


    // UPDATE SERVICE
    public Object updateProduct(String productId , ProductRequest productRequest) {
        Product product = productRepository.findById(productId).orElse(null);
        if(product==null){
            GenericResponseBody genericResponseBody= handler.genericResponseBodyMethod(404,"Failed to update Product with id "+productId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(genericResponseBody);
        }

        if(productRequest.getName() != null){
            product.setName(productRequest.getName());
        }
        if(productRequest.getDescription() != null){
            product.setDescription(productRequest.getDescription());
        }
        if(productRequest.getCategory() != null){
            categoryRepository.findById(productRequest.getCategory()).ifPresent(product::setCategory);
        }
        if(productRequest.getProductCount() != product.getProductCount()){
            product.setProductCount(productRequest.getProductCount());
        }
        if(productRequest.getSQNumber() != null){
            product.setSQNumber(productRequest.getSQNumber());
        }
        if(productRequest.getPrice() != product.getPrice()){
            product.setPrice(productRequest.getPrice());
        }

        try {
            Product newProduct = productRepository.save(product);
            ProductResponse productResponse = productResponseMethod(newProduct);
            GenericResponseBody genericResponseBody = handler.genericResponseBodyMethod(201,"Product with new Data has been updated successfully",
                    productResponse);
            return ResponseEntity.status(HttpStatus.CREATED).body(genericResponseBody);
        }catch (Exception e){
            GenericResponseBody genericResponseBody = handler.genericResponseBodyMethod(500,e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(genericResponseBody);
        }
    }

    // DELETE SERVICE
    public Object deleteProducts(String productId) {
        productRepository.deleteById(productId);
        GenericResponseBody genericResponseBody = handler.genericResponseBodyMethod(202,
                "Product with id "+productId+" has been deleted successfully");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(genericResponseBody);
    }

    Product newProductMethod(ProductRequest productRequest){
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setProductCount(productRequest.getProductCount());
        product.setSQNumber(productRequest.getSQNumber());
        return product;
    }

    ProductResponse productResponseMethod(Product product){
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setCategory(product.getCategory().getCategoryName());
        return  productResponse;
    }
}