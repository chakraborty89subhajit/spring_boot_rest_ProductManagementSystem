package com.example.ProductManagementSystem.service;

import com.example.ProductManagementSystem.dto.ProductDto;
import com.example.ProductManagementSystem.dto.ProductResponse;
import com.example.ProductManagementSystem.model.Product;

import java.util.List;

public interface ProductService {
    public Boolean saveProduct(ProductDto productDto);
    public List<ProductDto> getAllProducts();
    public ProductDto getProductById(Integer id);
    public Boolean deleteProduct(Integer id);

    //implementing pagination and sorting
    public ProductResponse getProductsWithPagination(int pageNO,
                                                     int pageSize,
                                                     String sortBy,
                                                     String sortDir);
}
