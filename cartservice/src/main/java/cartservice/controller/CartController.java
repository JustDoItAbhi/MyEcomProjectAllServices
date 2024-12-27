package cartservice.controller;

import cartservice.dtos.*;
import cartservice.entity.Products;
import cartservice.entity.UserDetails;
import cartservice.service.IcartServices;
import cartservice.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final IcartServices icartServices;
    private final UserServices userServices;

    @Autowired
    public CartController(IcartServices icartServices, UserServices userServices) {
        this.icartServices = icartServices;
        this.userServices = userServices;
    }

    @GetMapping("/login/{userID}")
    public ResponseEntity<UserDetails> getByiD(@PathVariable("userID") String userID) {// user login
        return ResponseEntity.ok(icartServices.getUser(userID));
    }

    @PostMapping("/add")
    public ResponseEntity<CartResposneDtos> getItams(@RequestBody CartRequestDto dtos) { //select product for cart
        return ResponseEntity.ok(icartServices.addItemToCart(dtos));
    }

    @DeleteMapping("/userdelete/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("id") long id) {// delete details user by id
        return ResponseEntity.ok(icartServices.deleteUser(id));
    }

    @GetMapping("/getByid/{id}")
    public ResponseEntity<ProductResponseDto> getById(@PathVariable("id") long id) {//fetch product by id for practice
        return ResponseEntity.ok(icartServices.getByIds(id));
    }

    @DeleteMapping("/deleteCart/userId/{userId}/productId/{productId}")
    public ResponseEntity<CartResposneDtos> removeCart(@PathVariable("userId") String userId, @PathVariable("productId") long productId) {// delete product from cart
        return ResponseEntity.ok(icartServices.removeItemFromCart(userId, productId));
    }

    @GetMapping("/getCartById/{userId}")// MOVED TO ORDER SERIVES
    public ResponseEntity<CartResposneDtos> ConfirmedCart(@PathVariable("userId") String userId) {// get cart by id
        return ResponseEntity.ok(icartServices.confirmCart(userId));
    }

    @GetMapping("/getallCart")
    public ResponseEntity<List<CartItemResponseDto>> getAllItems() {
        return ResponseEntity.ok(icartServices.getAllCartItems());
    }
@GetMapping("/")
public ResponseEntity<List<ProductResponseDto>> getAllProducts() {//get all products to test
    return ResponseEntity.ok(icartServices.getAllProducts());
}

    @GetMapping("/GETUSERROLE")
    public ResponseEntity<String> getUSERrOLE() {// GET USER ROLE
        return ResponseEntity.ok(icartServices.getUserRoles());
    }

    @DeleteMapping("/deleteCartById/{id}")
    public ResponseEntity<Boolean> deleteCartById(@PathVariable("id") long id) {// delete details user by id
        return ResponseEntity.ok(icartServices.deleteCart(id));
    }
}
