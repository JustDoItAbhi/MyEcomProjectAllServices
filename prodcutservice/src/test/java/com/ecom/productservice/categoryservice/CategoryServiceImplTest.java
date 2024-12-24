package com.ecom.productservice.categoryservice;

import com.ecom.productservice.category.categoryservice.CategoryServiceImpl;
import com.ecom.productservice.entity.BaseModels;
import com.ecom.productservice.entity.Categoryes;
import com.ecom.productservice.entity.Products;
import com.ecom.productservice.product.repository.CategoryRespository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class CategoryServiceImplTest {
    @Mock
    private CategoryRespository categoryRespository;
    @InjectMocks
    private CategoryServiceImpl categoryService;
    private BaseModels baseModels;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testCategorypricebyproductlist(){
        Random random=new Random();
        long id = random.nextLong();
        Optional<Categoryes>categoryOptionalMockData=createCategoryTest();
        Mockito.when(categoryRespository.findById(id)).thenReturn(categoryOptionalMockData);
        int actualTotalcost=categoryService.findByPrice(id);
        int expected=300;
        Assertions.assertEquals(actualTotalcost,expected);


    }
    public Optional<Categoryes> createCategoryTest(){
        Random random=new Random();
        long id = random.nextLong();
        Categoryes categoryes=new Categoryes();
        categoryes.setId(id);
        categoryes.setCategoryName("medicine");
        categoryes.setCategoryDescription("all type of medicine");

        Products products=new Products();
        products.setName("rumal");
        products.setId(id);
        products.setCategoryes(categoryes);
        products.setPrice(100);


        Products products1=new Products();
        products1.setId(id);
        products1.setName("rumalaa");
        products1.setCategoryes(categoryes);
        products1.setPrice(200);

        List<Products> productsList=new ArrayList<>();
        productsList.add(products);
        productsList.add(products1);

        categoryes.setProducts(productsList);
        return Optional.of(categoryes);




    }
}
