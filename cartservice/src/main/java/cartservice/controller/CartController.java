package cartservice.controller;

import cartservice.dtos.*;
import cartservice.service.IcartServices;
import cartservice.expcetions.expectionsfiles.CartNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final IcartServices icartServices;

    public CartController(IcartServices icartServices) {
        this.icartServices = icartServices;
    }

    @GetMapping("/getallCart")// get all carts
    public ResponseEntity<List<CartItemResponseDto>> getAllCartItems() throws CartNotFoundException {
        return ResponseEntity.ok(icartServices.getAllCartItems());
    }

    @PostMapping("/add/{email}")// add cart if not available
    public ResponseEntity<CartResposneDtos> addItemToCart(
            @PathVariable("email") String email,
            @RequestBody CartRequestDto dtos)  throws CartNotFoundException { //select product for cart
        return ResponseEntity.ok(icartServices.addItemToCart(email,dtos));
    }


    @DeleteMapping("/deleteProductFromCart/cartId/{cartId}/productId/{productId}")// delete product from cartItems
    public ResponseEntity<CartResposneDtos> removeCart(@PathVariable("cartId") long cartId, @PathVariable("productId") long productId) throws CartNotFoundException {// delete product from cart
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
    @GetMapping("/getProductByid/{id}")// get product to test if everything works fine
    public ResponseEntity<ProductResponseDto> getById(@PathVariable("id") long id) {//fetch product by id for practice
        return ResponseEntity.ok(icartServices.getProductByIds(id));
    }
    @GetMapping("/")// get all prodcut for practice
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {//get all products to test
        return ResponseEntity.ok(icartServices.getAllProducts());
    }
}
