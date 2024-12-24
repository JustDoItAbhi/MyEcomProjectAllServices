package cartservice.service;

import cartservice.client.ProductServiceClient;
import cartservice.client.UserclientRestTemplate;
import cartservice.dtos.CartItemResponseDto;
import cartservice.dtos.CartRequestDto;
import cartservice.dtos.CartResposneDtos;
import cartservice.dtos.ProductResponseDto;
import cartservice.entity.CartItems;
import cartservice.entity.Carts;
import cartservice.entity.Products;
import cartservice.entity.UserDetails;
import cartservice.mapper.CartMapper;
import cartservice.mapper.Mapper;
import cartservice.mapper.ProductMapper;
import cartservice.mapper.UserMapper;
import cartservice.repository.CartItemsRepository;
import cartservice.repository.CartRepository;
import cartservice.repository.ProductRepository;
import cartservice.repository.UserDetailsReposirtoy;
import cartservice.userdtos.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements IcartServices {

    private final CartRepository cartRepository;
    private final ProductServiceClient productServiceClient;
    private final UserDetailsReposirtoy userDetailsReposirtoy;
    private final ProductRepository productRepository;
    private final CartItemsRepository cartItemsRepository;


    public CartServiceImpl(CartRepository cartRepository, ProductServiceClient productServiceClient,
                           UserDetailsReposirtoy userDetailsReposirtoy, ProductRepository productRepository,
                           CartItemsRepository cartItemsRepository) {
        this.cartRepository = cartRepository;
        this.productServiceClient = productServiceClient;
        this.userDetailsReposirtoy = userDetailsReposirtoy;
        this.productRepository = productRepository;
        this.cartItemsRepository = cartItemsRepository;
    }

    @Override
    public CartResposneDtos addItemToCart(CartRequestDto dto) {
        Carts carts = new Carts();
        carts.setUserId(dto.getUserId());
        List<CartItems> cartItemsList = new ArrayList<>();
        for (CartItems cartItems : dto.getItem()) {
            ProductResponseDto responseDto =productServiceClient.fetchProductbYiD(cartItems.getProductId());

            if (responseDto == null) {
                throw new RuntimeException("Product not found: " + cartItems.getProductId());
            }

            cartItems.setProductId(responseDto.getId());
            cartItems.setProductName(responseDto.getName());
            cartItems.setQuantity(cartItems.getQuantity());
            cartItems.setPrice(responseDto.getPrice());
            cartItemsList.add(cartItems);
        }
        double amount = 0.0;
        for (int i = 0; i < cartItemsList.size(); i++) {
            amount += cartItemsList.get(i).getPrice() * cartItemsList.get(i).getQuantity();
        }
        carts.setTotal(amount);
        carts.setItems(cartItemsList);
        Carts savedCart = cartRepository.save(carts);
        return CartMapper.fromCart(savedCart);
    }

    @Override
    public CartResposneDtos removeItemFromCart(String userId, long productId) {
        Carts cart = cartRepository.findByUserId(userId).orElseThrow(
                () -> new RuntimeException("Cart not found for user: " + userId));
        List<CartItems> updateCartItems = new ArrayList<>();
        for (CartItems items : cart.getItems()) {
            if (items.getProductId() != productId) {
                updateCartItems.add(items);
            }
        }
        double totalcost = 0.0;
        int n = updateCartItems.size();
        for (int i = 0; i < n; i++) {
            totalcost += updateCartItems.get(i).getQuantity() * updateCartItems.get(i).getPrice();
        }
        cart.setTotal(totalcost);
        if (updateCartItems.size() == cart.getItems().size()) {
            throw new RuntimeException("CART ID NOT FOUND " + productId);
        }
        cart.setItems(updateCartItems);
        cartRepository.save(cart);
        cartItemsRepository.deleteById(productId);

        return CartMapper.fromCart(cart);
    }

    @Override
    public CartResposneDtos confirmCart(String userId) {
        Carts cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));

        // Perform confirmation logic, e.g., saving the cart as an order
        // ...

        // Optionally clear the cart
//        cart.getItems().clear();
//        cart.setTotal(0);
//        cartRepository.save(cart);
        return CartMapper.fromCart(cart);

    }

    @Override
    public boolean deleteUser(long id) {
        userDetailsReposirtoy.deleteById(id);
        return true;
    }

    @Override
    public CartResposneDtos getById(long id) {
//        Carts carts=new Carts();
//        for(CartItems cartsitems:carts.getItems()){
//            CartItems item = productServiceClient.fetchProductbYiD(id)));
//            carts.addItem(item);
//        }
//        Carts savedCart = cartRepository.save(carts);
        return null;
    }

    @Override
    public ProductResponseDto getByIds(long id) {
        ProductResponseDto responseDto =productServiceClient.fetchProductbYiD(id);

        if (responseDto == null) {
            throw new RuntimeException("Product not found: " +id);
        }

        return responseDto;
    }

    @Override
    public List<CartItemResponseDto> getAllCartItems() {
        List<CartItems> items = cartItemsRepository.findAll();
        List<CartItemResponseDto> dtos = new ArrayList<>();
        for (CartItems cartItems : items) {
            dtos.add(Mapper.fromcartItems(cartItems));
        }
        return dtos;
    }

    @Override
    public CartItemResponseDto getCartItemById(String userId) {
        return null;
    }

    @Override
    public String getUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            return jwt.getClaimAsStringList("roles").toString(); // Extract "roles" claim
        }
        return "No roles available";
    }

    @Override
    public UserDetails getUser(String userEmail) {
        // Validate if the user exists
        Optional<UserDetails> existingUser = userDetailsReposirtoy.findByUserEmail(userEmail);
        if (existingUser.isEmpty()) {
            throw new RuntimeException("PLEASE SIGN IN AGAIN OR CALL USER SEPERATLY FROM USER SERVICE " + userEmail);
        }

        return existingUser.get();
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
List<ProductResponseDto>responseDtos=productServiceClient.fetchProduct();
//List<Products>products=new ArrayList<>();
//for(ProductResponseDto dto:responseDtos){
//    products.add(ProductMapper.fromProductResponseDto(dto));
//}

        return responseDtos;
    }

    @Override
    public boolean deleteCart(long cartId) {
        cartRepository.deleteById(cartId);
        return true;
    }


}

