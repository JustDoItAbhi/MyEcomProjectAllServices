package cartservice.controller;

import cartservice.client.dto.ProductResponseDto;
import cartservice.client.dto.UserResponseDto;
import cartservice.dtos.*;
import cartservice.securityconfigrations.expcetions.expectionsfiles.InvalidEmailException;
import cartservice.securityconfigrations.expcetions.expectionsfiles.UserNotExistsException;
import cartservice.service.IcartServices;
import cartservice.securityconfigrations.expcetions.expectionsfiles.CartNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final IcartServices icartServices;
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9._%+-]+@(gmail\\.com|yahoo\\.com)$";

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
        if(!email.matches(EMAIL_PATTERN)){
            throw new InvalidEmailException("Invalid email! Only Gmail and Yahoo emails are allowed."+email);
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
        if(!email.matches(EMAIL_PATTERN)){
            throw new UserNotExistsException("Invalid email! Only Gmail and Yahoo emails are allowed."+email);
        }
        return ResponseEntity.ok(icartServices.testUser(email));
    }
}
