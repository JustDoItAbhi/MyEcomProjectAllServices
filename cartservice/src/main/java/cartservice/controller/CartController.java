package cartservice.controller;

import cartservice.client.UserResponseDto;
import cartservice.dtos.*;
import cartservice.service.IcartServices;
import cartservice.expcetions.expectionsfiles.CartNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
        if (dtos.getItem() == null || dtos.getItem().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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
    @GetMapping("/debug") // admin
    public ResponseEntity<String> debugAdminRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not authenticated");
        }

        // Retrieve roles from authentication
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));

        return ResponseEntity.ok("User roles: " + roles);
    }
    @GetMapping("/getUser/{email}")// get all prodcut for practice
    public ResponseEntity<UserResponseDto> getuserforTESTING(@PathVariable ("email")String email ){//get all products to test
        return ResponseEntity.ok(icartServices.testUser(email));
    }
}
