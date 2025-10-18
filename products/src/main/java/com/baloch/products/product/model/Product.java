package com.baloch.products.product.model;

import com.baloch.products.category.model.Category;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private String description;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    private Category category;

    private int productCount;

    private String SQNumber;

    private float price;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedat;

    public Product() {

    }
}
