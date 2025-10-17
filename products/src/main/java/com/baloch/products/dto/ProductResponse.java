package com.baloch.products.dto;

import com.baloch.products.models.Category;
import com.baloch.products.models.Product;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.http.HttpStatusCode;

import java.sql.Timestamp;

@Data
public class ProductResponse {

    private String name;

    private String description;

    private int productCount;

    private float price;

}
