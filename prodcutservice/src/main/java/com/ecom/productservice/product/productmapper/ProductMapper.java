package com.ecom.productservice.product.productmapper;

import com.ecom.productservice.entity.Products;
import com.ecom.productservice.product.dtos.ProductRequestDto;
import com.ecom.productservice.product.dtos.ProductResponseDto;

public class ProductMapper {
    public static ProductResponseDto fromEntity(Products products){
        ProductResponseDto responseDto=new ProductResponseDto();
        responseDto.setName(products.getName());
        responseDto.setId(products.getId());
        responseDto.setBrand(products.getBrand());
        responseDto.setPrice(products.getPrice());
        responseDto.setStock(products.getStock());
        responseDto.setDescription(products.getDescription());
        responseDto.setImage(products.getImage());
        responseDto.setCategoryes(products.getCategoryes());
        return responseDto;
    }
    public static ProductResponseDto forSerching(Products products){
        ProductResponseDto responseDto=new ProductResponseDto();
        responseDto.setName(products.getName());
        responseDto.setId(products.getId());
        responseDto.setBrand(products.getBrand());
        responseDto.setPrice(products.getPrice());
        responseDto.setStock(products.getStock());
        responseDto.setDescription(products.getDescription());
        responseDto.setImage(products.getImage());
//        responseDto.setCategoryes(products.getCategoryes());
        return responseDto;
    }
    public static Products fromDto(ProductRequestDto requestDto){
        Products products = new Products();
        products.setName(requestDto.getName());
        products.setBrand(requestDto.getBrand());
        products.setPrice(requestDto.getPrice());
        products.setStock(requestDto.getStock());
        products.setDescription(requestDto.getDescription());
        products.setImage(requestDto.getImage());
        return products;
    }
}
