package com.example.ProductManagementSystem.controller;

import com.example.ProductManagementSystem.dto.ProductDto;
import com.example.ProductManagementSystem.dto.ProductResponse;
import com.example.ProductManagementSystem.exception.BadRequestException;
import com.example.ProductManagementSystem.model.Product;
import com.example.ProductManagementSystem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
   private ProductService productService;
@PostMapping("/save-product")
    public ResponseEntity<?> saveProduct(@RequestBody ProductDto productDto) {
    try {
        validationProduct(productDto);
        Boolean saveProduct = productService.saveProduct(productDto);

        if (!saveProduct) {
            return new ResponseEntity<>("product not saved",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    catch(BadRequestException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

    }
    catch(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>("product saves successfully",HttpStatus.CREATED);

        }



    private void validationProduct(ProductDto productDto) {

        //  Validate name
        if (productDto.getName() == null || productDto.getName().trim().isEmpty()) {
            throw new BadRequestException("Name field is empty or null");
        }

        //  Validate description
        if (productDto.getDescription() == null ||
                productDto.getDescription().trim().isEmpty()) {
            throw new BadRequestException("Description field is empty or null");
        }

        //  Description length validation
        int descLength = productDto.getDescription().trim().length();
        if (descLength < 3 || descLength > 10) {
            throw new BadRequestException
                    ("Description length must be between 3 and 10 characters");
        }
    }




    @GetMapping("/products")
        public ResponseEntity<?> getproducts(){
    List<ProductDto> allProducts= null;
    try{
        allProducts = productService.getAllProducts();
        if(CollectionUtils.isEmpty(allProducts)){
            return new ResponseEntity<>("blank list",HttpStatus.NO_CONTENT);
        }
    }catch(Exception e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<>(allProducts,HttpStatus.OK);
        }


        @GetMapping("/product/{id}")
        public ResponseEntity<?> getproduct(@PathVariable (name="id") Integer id){
    ProductDto productDto = null;
    try{
        productDto = productService.getProductById(id);
        if(ObjectUtils.isEmpty(productDto)){
            return new ResponseEntity<>
                    ("product not found with id: "+id,HttpStatus.NOT_FOUND);
        }
    }catch(Exception e){

      return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(productDto,HttpStatus.OK);
        }


        @DeleteMapping("/product/{id}")
        public ResponseEntity<?> deleteproduct(@PathVariable (name="id") Integer id){
    Boolean deleteProduct = null;
    try{
        deleteProduct = productService.deleteProduct(id);
        if(!deleteProduct){
            return new ResponseEntity<>(
                    "product can not be deleted",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }catch(Exception e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>("product delete successfully",HttpStatus.OK);
        }



        @GetMapping("/page-products")
        public ResponseEntity<?> getProductsPagenate(
                @RequestParam(name="pageno", defaultValue = "0") int pageNo,
                @RequestParam(name="pagesize", defaultValue = "2") int pageSize,
                @RequestParam(name="sortby", defaultValue = "id") String sortBy,
                @RequestParam(name="sortdir", defaultValue = "asc") String sortDir

        ){
            ProductResponse productResponse = null;
            String name= null;
            name.toUpperCase();
            try{
                productResponse= productService.getProductsWithPagination(pageNo,
                        pageSize,
                        sortBy,
                        sortDir);
                if(ObjectUtils.isEmpty(productResponse)){
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
            }catch(Exception e){
                return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(productResponse,HttpStatus.OK);
        }

    }

