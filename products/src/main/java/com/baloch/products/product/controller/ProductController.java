package com.baloch.products.product.controller;

import com.baloch.products.product.dto.ProductRequest;
import com.baloch.products.product.service.ProductServices;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/product")
public class ProductController {
    private ProductServices productServices;

    @GetMapping("/{productId}")
    public Object getSingleProduct(@PathVariable String productId,
                                   @RequestHeader("X-User-Username") String username){
        System.out.println(username);
        return productServices.getSingleProducts(productId);
    }

    @GetMapping
    public Object Product(){
        return productServices.getAllProducts();
    }

    @PostMapping
    public Object createProduct(@RequestBody ProductRequest productRequest ){
        return productServices.createProduct(productRequest);
    }

    @PatchMapping("/{productId}")
    public Object updateProduct(@PathVariable String productId,@RequestBody ProductRequest productRequest){
        return productServices.updateProduct(productId,productRequest);
    }

    @DeleteMapping("/{productId}")
    public Object deleteProduct(@PathVariable String productId){
        return productServices.deleteProducts(productId);
    }

}
