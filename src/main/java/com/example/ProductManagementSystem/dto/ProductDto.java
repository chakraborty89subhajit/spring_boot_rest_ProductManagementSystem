package com.example.ProductManagementSystem.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class ProductDto {
    private Integer id;

   // @NotBlank
    private String name;

  //@NotEmpty
  //@Size(min=3,max=10,message="description max 10 min 3")
    private String description;


    private Double price;
    private Integer quantity;


}
