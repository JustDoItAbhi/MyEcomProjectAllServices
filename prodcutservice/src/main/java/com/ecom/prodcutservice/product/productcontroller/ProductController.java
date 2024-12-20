package com.ecom.prodcutservice.product.productcontroller;

import com.ecom.prodcutservice.category.categoryexpections.CategoryNotFoundException;
import com.ecom.prodcutservice.product.dtos.ProductRequestDto;
import com.ecom.prodcutservice.product.dtos.ProductResponseDto;
import com.ecom.prodcutservice.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @PostMapping("/create")
public ResponseEntity<ProductResponseDto>createProduct(@RequestBody ProductRequestDto requestDto)
        throws CategoryNotFoundException {
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
                                                            @RequestBody ProductRequestDto requestDto) throws CategoryNotFoundException {
        return ResponseEntity.ok(productService.updateProduct(id,requestDto));
}
@GetMapping("/")
    public ResponseEntity<List<ProductResponseDto>> getAllProduct(){
        return ResponseEntity.ok(productService.getAllProducts());
}
@GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getById(@PathVariable ("id")long id){
        return ResponseEntity.ok(productService.getProductById(id));
}
}
