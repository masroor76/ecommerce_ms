package com.baloch.products.category.model;

import com.baloch.products.product.model.Product;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String categoryId;

    private String categoryName;
    private String categoryDesc;
    private List<String> keywords;

    @JsonManagedReference
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products=new ArrayList<>();

    @CreationTimestamp
    private Timestamp createdAt;

    public Category() {

    }
}
