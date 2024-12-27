package com.ecom.productservice.product.service;

import com.ecom.productservice.category.categoryexpections.CategoryNotFoundExceptions;
import com.ecom.productservice.entity.Categoryes;
import com.ecom.productservice.entity.Products;
import com.ecom.productservice.product.dtos.ProductRequestDto;
import com.ecom.productservice.product.dtos.ProductResponseDto;
import com.ecom.productservice.product.productmapper.ProductMapper;
import com.ecom.productservice.repository.CategoryRespository;
import com.ecom.productservice.repository.ProductRespository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {
    @Mock
    private ProductRespository productRespository;
    @Mock
    private CategoryRespository categoryRespository;
    @Mock
    private ProductMapper productMapper;
    @InjectMocks
    private ProductServiceImpl productServiceImpl;
    private ProductService productService;
    private ProductRequestDto requestDto;
    private ProductResponseDto responseDto;
    private Products products;
    private Categoryes categoryes;

    @BeforeEach
    void createProduct() {
        MockitoAnnotations.initMocks(this);
        categoryes=new Categoryes();
        categoryes.setCategoryDescription("clothing");
        categoryes.setCategoryName("cloths");

        products=new Products();
        products.setName("shirts");
        products.setBrand("ZARA");
        products.setCategoryes(categoryes);
        products.setPrice(100);
        products.setImage("google.com");
        products.setDescription("winterwear");

        products.setStock(10);
        responseDto=new ProductResponseDto();
//        responseDto.setId(1L);
        responseDto.setPrice(120);
        responseDto.setImage("google.com");
        responseDto.setBrand("update product");
        responseDto.setStock(10);
        responseDto.setCategoryes(categoryes);
        responseDto.setDescription("winderwear");
        responseDto.setName("shirts");

        requestDto=new ProductRequestDto();
        requestDto.setBrand("update product");
        requestDto.setName("shirts");
        requestDto.setPrice(100);
        requestDto.setStock(10);
        requestDto.setImage("google.com");
        requestDto.setDescription("winterwear");
        requestDto.setCategoryesId(1L);
    }
//    @Test
    void updateProducttest() throws CategoryNotFoundExceptions {
        try (MockedStatic<ProductMapper> mapperMockedStatic = mockStatic(ProductMapper.class)) {

            when(productRespository.findById(1L)).thenReturn(Optional.of(products));
            when(productRespository.save(any(Products.class))).thenReturn(products);
            when(categoryRespository.findById(1L)).thenReturn(Optional.of(categoryes));
          mapperMockedStatic.when(()->ProductMapper.fromEntity(any(Products.class))).thenReturn(responseDto);

            ProductResponseDto responseDto1 = productServiceImpl.updateProduct(1L, requestDto);

            assertNotNull(responseDto1);
            assertEquals("update product", responseDto1.getBrand());
            assertEquals(120, responseDto1.getPrice());
            verify(productRespository, times(1)).findById(1L);
            verify(productRespository, times(1)).save(any(Products.class));

        }

    }
    @AfterEach
    public void cleanUp() {
       productRespository.deleteAll();
        categoryRespository.deleteAll();
    }
}