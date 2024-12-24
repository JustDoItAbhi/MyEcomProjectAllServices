package com.ecom.productservice.product.service;

import com.ecom.productservice.category.categoryexpections.CategoryNotFoundExceptions;
import com.ecom.productservice.entity.Categoryes;
import com.ecom.productservice.entity.Products;
import com.ecom.productservice.product.dtos.ProductRequestDto;
import com.ecom.productservice.product.dtos.ProductResponseDto;
import com.ecom.productservice.product.repository.CategoryRespository;
import com.ecom.productservice.product.repository.ProductRespository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//@SpringBootTest
//@Transactional
//@AutoConfigureMockMvc

public class ProductServiceImplIntegrationTest {
    @Autowired
    private ProductServiceImpl productServiceImpl;

    @Autowired
    private ProductRespository productRespository;

    @Autowired
    private CategoryRespository categoryRespository;
    private Categoryes category;
    private Products product;
    @BeforeEach
    void setUp() {
        // Create and save a test category
        category = new Categoryes();
        category.setCategoryName("Clothing");
        category.setCategoryDescription("Various types of clothing");
        categoryRespository.save(category);

        // Create and save a test product
        product = new Products();
        product.setName("Shirt");
        product.setBrand("ZARA");
        product.setPrice(50);
        product.setStock(100);
        product.setImage("https://example.com/shirt.jpg");
        product.setDescription("Stylish shirt");
        product.setCategoryes(category);
        productRespository.save(product);

    }


//    @Test
    void testCreateProduct() throws CategoryNotFoundExceptions{
        // Arrange
        ProductRequestDto requestDto = new ProductRequestDto();
        requestDto.setName("New Shirt");
        requestDto.setBrand("H&M");
        requestDto.setPrice(60);
        requestDto.setStock(200);
        requestDto.setImage("https://example.com/new-shirt.jpg");
        requestDto.setDescription("New stylish shirt");
        requestDto.setCategoryesId(category.getId()); // Link to the existing category

        // Act
        ProductResponseDto responseDto = productServiceImpl.createProduct(requestDto);

        // Assert
        assertNotNull(responseDto);
        assertEquals("New Shirt", responseDto.getName());
        assertEquals("H&M", responseDto.getBrand());
        assertEquals(60, responseDto.getPrice());
        assertEquals(200, responseDto.getStock());
        assertEquals(category.getId(), responseDto.getCategoryes().getId());
    }

//    @Test
    void testUpdateProduct() throws CategoryNotFoundExceptions {
        // Arrange
        ProductRequestDto requestDto = new ProductRequestDto();
        requestDto.setName("Updated Shirt");
        requestDto.setBrand("ZARA");
        requestDto.setPrice(70);
        requestDto.setStock(150);
        requestDto.setImage("https://example.com/updated-shirt.jpg");
        requestDto.setDescription("Updated stylish shirt");
        requestDto.setCategoryesId(category.getId());

        // Act
        ProductResponseDto responseDto = productServiceImpl.updateProduct(product.getId(), requestDto);

        // Assert
        assertNotNull(responseDto);
        assertEquals("Updated Shirt", responseDto.getName());
        assertEquals("ZARA", responseDto.getBrand());
        assertEquals(70, responseDto.getPrice());
        assertEquals(150, responseDto.getStock());
        assertEquals(category.getId(), responseDto.getCategoryes().getId());

    }
    @AfterEach
    public void cleanUp() {
        productRespository.deleteAll();
        categoryRespository.deleteAll();
    }

}
