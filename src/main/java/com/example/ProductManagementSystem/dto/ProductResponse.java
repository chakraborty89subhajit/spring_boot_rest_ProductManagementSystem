package com.example.ProductManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private List<ProductDto> products;
    private long totalElements;
    private int totalPages;
    private boolean isFirst;
    private boolean isLast;
    private int pageNo;
    private int pageSize;
}
