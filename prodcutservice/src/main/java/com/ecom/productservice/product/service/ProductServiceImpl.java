package com.ecom.productservice.product.service;

import com.ecom.productservice.category.categoryexpections.CategoryNotFoundExceptions;
import com.ecom.productservice.entity.Categoryes;
import com.ecom.productservice.entity.Products;
import com.ecom.productservice.product.dtos.ProductRequestDto;
import com.ecom.productservice.product.dtos.ProductResponseDto;
import com.ecom.productservice.product.exceptions.ProductNotFoundException;
import com.ecom.productservice.product.productmapper.ProductMapper;
import com.ecom.productservice.repository.CategoryRespository;
import com.ecom.productservice.repository.ProductRespository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRespository productRespository;
    private CategoryRespository categoryRespository;


    public ProductServiceImpl(ProductRespository productRespository, CategoryRespository categoryRespository) {
        this.productRespository = productRespository;
        this.categoryRespository = categoryRespository;

    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto requestDto) throws CategoryNotFoundExceptions {
        Categoryes savedCategory = categoryRespository.findById(requestDto.getCategoryesId()).orElseThrow(
                () -> new CategoryNotFoundExceptions("CATEGORY NOT FOUND " + requestDto.getCategoryesId()));
        Products products = new Products();
        products.setName(requestDto.getName());
        products.setBrand(requestDto.getBrand());
        products.setPrice(requestDto.getPrice());
        products.setStock(requestDto.getStock());
        products.setDescription(requestDto.getDescription());
        products.setImage(requestDto.getImage());
        products.setCategoryes(savedCategory);
        productRespository.save(products);
        categoryRespository.save(savedCategory);
        return ProductMapper.fromEntity(products);
    }

    @Override
    public ProductResponseDto updateProduct(long id, ProductRequestDto requestDto) throws CategoryNotFoundExceptions {
        Products products=productRespository.findById(id).orElseThrow(
                ()->new ProductNotFoundException("PRODUCT NOT FOUND "+id));
        Categoryes savedCategory=categoryRespository.findById(requestDto.getCategoryesId()).orElseThrow(
                ()->new CategoryNotFoundExceptions("CATEGORY NOT FOUND "+requestDto.getCategoryesId()));
        products.setName(requestDto.getName());
        products.setBrand(requestDto.getBrand());
        products.setPrice(requestDto.getPrice());
        products.setStock(requestDto.getStock());
        products.setDescription(requestDto.getDescription());
        products.setImage(requestDto.getImage());
        products.setCategoryes(savedCategory);
        productRespository.save(products);
        return ProductMapper.fromEntity(products);
    }

    @Override
    public ProductResponseDto getProductById(long id) {
        Optional<Products>savedProduct=productRespository.findById(id);
        if(savedProduct.isEmpty()){
            throw new ProductNotFoundException("PRODUCT NOT FOUNT "+id);
        }
        Products products=savedProduct.get();
        return ProductMapper.forSerching(products);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {

        List<Products>productsList=productRespository.findAll();
        List<ProductResponseDto>responseDtos=new ArrayList<>();
        for(Products products:productsList){
            responseDtos.add(ProductMapper.forSerching(products));
        }
        return responseDtos;
    }

    @Override
    public boolean deleteProduct(long id) {

        Products savedProduct = productRespository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("product id not found " + id));
        if (savedProduct.getCategoryes() == null||savedProduct.getPrice() == 0 || savedProduct.getStock() == 0) {
            productRespository.deleteById(id);
            return true;
        }
        return false;
}

    @Override
    public boolean deleteList() {
        List<Products> savedProduct = productRespository.findAll();
        for(Products productList:savedProduct){
            if(productList.getDescription().equals("IOS22")){
                productRespository.deleteAll();
                return true;
            }
        }
                return false;
            }
    @Override
    public  String getUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            return jwt.getClaimAsStringList("roles").toString(); // Extract "roles" claim
        }
        return "No roles available";
    }
        }
