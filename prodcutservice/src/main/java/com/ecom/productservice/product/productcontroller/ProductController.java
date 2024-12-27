package com.ecom.productservice.product.productcontroller;

import com.ecom.productservice.category.categoryexpections.CategoryNotFoundExceptions;
import com.ecom.productservice.product.dtos.ProductRequestDto;
import com.ecom.productservice.product.dtos.ProductResponseDto;
import com.ecom.productservice.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/")
    public ResponseEntity<List<ProductResponseDto>> getAllProduct(){
        return ResponseEntity.ok(productService.getAllProducts());
    }
    @PostMapping("/create")
public ResponseEntity<ProductResponseDto>createProduct(@RequestBody ProductRequestDto requestDto)
        throws CategoryNotFoundExceptions {
    return ResponseEntity.ok(productService.createProduct(requestDto));
}
@DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable ("id") long id){
        return ResponseEntity.ok(productService.deleteProduct(id));
}
//@DeleteMapping("/")
//    public ResponseEntity<Boolean>deleteAll(){
//        return ResponseEntity.ok(productService.deleteList());
//}
@PostMapping("/UPDATE/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable("id")long id,
                                                            @RequestBody ProductRequestDto requestDto) throws CategoryNotFoundExceptions {
        return ResponseEntity.ok(productService.updateProduct(id,requestDto));
}

@GetMapping("/get/{id}")
    public ResponseEntity<ProductResponseDto> getById(@PathVariable ("id")long id){
        return ResponseEntity.ok(productService.getProductById(id));
}
    @GetMapping("/GETUSERROLE")
    public ResponseEntity<String> getUSERrOLE() {
        return ResponseEntity.ok(productService.getUserRoles());
    }
}
