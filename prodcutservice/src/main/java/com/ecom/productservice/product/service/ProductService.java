package com.ecom.productservice.product.service;

import com.ecom.productservice.category.categoryexpections.CategoryNotFoundExceptions;
import com.ecom.productservice.product.dtos.ProductRequestDto;
import com.ecom.productservice.product.dtos.ProductResponseDto;

import java.util.List;

public interface ProductService {
    ProductResponseDto createProduct(ProductRequestDto requestDto)
            throws CategoryNotFoundExceptions;// CREATE PRODUCT
    ProductResponseDto updateProduct(long id,ProductRequestDto requestDto) throws CategoryNotFoundExceptions;// UPDATE PRODUCT
    ProductResponseDto getProductById(long id);// GET PRODUCT BY ITS ID
    List<ProductResponseDto>getAllProducts();// GET ALL PRODUCTS
    boolean deleteProductifanyValueIsNull(long id);// DELETE PRODUCT
    boolean deleteList();// delete list of products
    String getUserRoles();// check the role of token provider
    String DeleteProduct(long id);
    List<ProductResponseDto> getProductByName(String name);
}

