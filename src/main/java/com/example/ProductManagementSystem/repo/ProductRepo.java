package com.example.ProductManagementSystem.repo;

import com.example.ProductManagementSystem.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product,Integer> {
}
