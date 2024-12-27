package cartservice.controller;

import cartservice.dtos.*;
import cartservice.service.IcartServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final IcartServices icartServices;

    public CartController(IcartServices icartServices) {
        this.icartServices = icartServices;
    }

    @GetMapping("/getallCart")// get all carts
    public ResponseEntity<List<CartItemResponseDto>> getAllItems() {
        return ResponseEntity.ok(icartServices.getAllCartItems());
    }

    @PostMapping("/add")// add cart if not available
    public ResponseEntity<CartResposneDtos> getItams(@RequestBody CartRequestDto dtos) { //select product for cart
        return ResponseEntity.ok(icartServices.addItemToCart(dtos));
    }


    @DeleteMapping("/deleteCart/userId/{cartId}/productId/{productId}")// delete product from cartItems
    public ResponseEntity<CartResposneDtos> removeCart(@PathVariable("cartId") long cartId, @PathVariable("productId") long productId) {// delete product from cart
        return ResponseEntity.ok(icartServices.removeItemFromCart(cartId, productId));
    }

    @GetMapping("/getCartById/{cartId}")// MOVED TO ORDER SERIVES
    public ResponseEntity<CartResposneDtos> ConfirmedCart(@PathVariable("cartId")long cartId) {// get cart by id
        return ResponseEntity.ok(icartServices.confirmCart(cartId));
    }

    @DeleteMapping("/deleteCartById/{id}")//delete cart by od
    public ResponseEntity<Boolean> deleteCartById(@PathVariable("id") long id) {// delete details user by id
        return ResponseEntity.ok(icartServices.deleteCart(id));
    }
    @GetMapping("/getByid/{id}")// get product to test if everything works fine
    public ResponseEntity<ProductResponseDto> getById(@PathVariable("id") long id) {//fetch product by id for practice
        return ResponseEntity.ok(icartServices.getByIds(id));
    }
    @GetMapping("/")// get all prodcut for practice
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {//get all products to test
        return ResponseEntity.ok(icartServices.getAllProducts());
    }
}
