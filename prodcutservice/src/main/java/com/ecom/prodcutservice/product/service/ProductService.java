package com.ecom.prodcutservice.product.service;

import com.ecom.prodcutservice.category.categoryexpections.CategoryNotFoundException;
import com.ecom.prodcutservice.product.dtos.ProductRequestDto;
import com.ecom.prodcutservice.product.dtos.ProductResponseDto;

import java.util.List;

public interface ProductService {
    ProductResponseDto createProduct(ProductRequestDto requestDto) throws CategoryNotFoundException;
    ProductResponseDto updateProduct(long id,ProductRequestDto requestDto) throws CategoryNotFoundException;
    ProductResponseDto getProductById(long id);
    List<ProductResponseDto>getAllProducts();
    boolean deleteProduct(long id);
    boolean deleteList();
    String getUserRoles();
}
