package com.ecom.productservice.product.service;

import com.ecom.productservice.category.categoryexpections.CategoryNotFoundExceptions;
import com.ecom.productservice.product.dtos.ProductRequestDto;
import com.ecom.productservice.product.dtos.ProductResponseDto;

import java.util.List;

public interface ProductService {
    ProductResponseDto createProduct(ProductRequestDto requestDto) throws CategoryNotFoundExceptions;
    ProductResponseDto updateProduct(long id,ProductRequestDto requestDto) throws CategoryNotFoundExceptions;
    ProductResponseDto getProductById(long id);
    List<ProductResponseDto>getAllProducts();
    boolean deleteProduct(long id);
    boolean deleteList();
    String getUserRoles();
}

