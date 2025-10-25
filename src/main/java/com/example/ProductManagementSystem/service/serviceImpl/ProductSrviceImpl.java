package com.example.ProductManagementSystem.service.serviceImpl;

import com.example.ProductManagementSystem.dto.ProductDto;
import com.example.ProductManagementSystem.model.Product;
import com.example.ProductManagementSystem.repo.ProductRepo;
import com.example.ProductManagementSystem.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductSrviceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ModelMapper mapper;


    /*
    //saving product in traditional way
    @Override
    public Boolean saveProduct(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());

        Product save = productRepo.save(product);

        if(ObjectUtils.isEmpty(save)){
            return false;
        }

        return true;
    }
*/


    //saving product in modelmapper way
    @Override
    public Boolean saveProduct(ProductDto productDto) {
        Product product = mapper.map(productDto,Product.class);
        Product save = productRepo.save(product);

        if(ObjectUtils.isEmpty(save)){
            return false;

        }else{

            return true;
        }


    }




    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> productList= productRepo.findAll();
        List<ProductDto> productDtoList = productList
                .stream()
                .map(Product->mapper.map(Product,ProductDto.class))
                .collect(Collectors.toList());
        return productDtoList;
    }

    @Override
    public ProductDto getProductById(Integer id) {

        Optional<Product> findProductById= productRepo.findById(id);

        if(findProductById.isPresent()){
            Product product = findProductById.get();
            ProductDto productDto = mapper.map(product,ProductDto.class);
            return productDto;

        }

        return null;
    }

    @Override
    public Boolean deleteProduct(Integer id) {
        Optional<Product> findProductById = productRepo.findById(id);
        if(findProductById.isPresent()){
  Product product= findProductById.get();
  productRepo.delete(product);
  return true;
        }
        return false;
    }
}
