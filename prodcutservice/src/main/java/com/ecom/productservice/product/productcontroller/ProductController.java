package com.ecom.productservice.product.productcontroller;

import com.ecom.productservice.category.categoryexpections.CategoryNotFoundExceptions;
import com.ecom.productservice.product.dtos.ProductRequestDto;
import com.ecom.productservice.product.dtos.ProductResponseDto;
import com.ecom.productservice.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")// COMMON ENDPOINT FOR ALL APIS
public class ProductController {// PRODUCT CONTROLLER
    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {// PRODUCT SERVICE AUTOWIRE
        this.productService = productService;
    }
    @GetMapping("/")// PUBLIC USE
    public ResponseEntity<List<ProductResponseDto>> getAllProduct(){// GET LIST OF PRODUCT
        return ResponseEntity.ok(productService.getAllProducts());
    }
    @GetMapping("/get/{id}")// PUBLIC USE
    public ResponseEntity<ProductResponseDto> getById(@PathVariable ("id")long id){// GET PRODUCT BY ITS ID
        return ResponseEntity.ok(productService.getProductById(id));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")// ADMIN USE
public ResponseEntity<ProductResponseDto>createProduct(@RequestBody ProductRequestDto requestDto)
        throws CategoryNotFoundExceptions {// CREATE PRODUCT
    return ResponseEntity.ok(productService.createProduct(requestDto));
}
@PreAuthorize("hasRole('ADMIN')")
@DeleteMapping("/deleteNullValueProduct/{id}")// ADMIN USE
    public ResponseEntity<Boolean> deleteProductifAnyValueIsNull(@PathVariable ("id") long id){// DELETE PRODUCT
        return ResponseEntity.ok(productService.deleteProductifanyValueIsNull(id));
}
@PreAuthorize("hasRole('ADMIN')")
@PutMapping("/UPDATE/{id}")// ADMIN USE
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable("id")long id,
                                                            @RequestBody ProductRequestDto requestDto)
        throws CategoryNotFoundExceptions {// UPDATE PRODUCT
        return ResponseEntity.ok(productService.updateProduct(id,requestDto));
}
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/GETUSERROLE")// ADMIN USE
    public ResponseEntity<String> getUSERrOLE() {// CHECK ROLE BY TOKEN
        return ResponseEntity.ok(productService.getUserRoles());
    }

@PreAuthorize("hasRole('ADMIN')")
@DeleteMapping("/deleteSingleProduct/{id}")// ADMIN USE
public ResponseEntity<String> deleteSingleProduct(@PathVariable ("id") long id){// DELETE PRODUCT
    return ResponseEntity.ok(productService.DeleteProduct(id));
}
}
//@DeleteMapping("/")
//    public ResponseEntity<Boolean>deleteAll(){
//        return ResponseEntity.ok(productService.deleteList());
//}
