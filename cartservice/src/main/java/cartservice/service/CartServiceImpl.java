package cartservice.service;

import cartservice.client.CallingUserService;
import cartservice.client.ProductServiceClient;
import cartservice.client.UserResponseDto;
import cartservice.dtos.CartItemResponseDto;
import cartservice.dtos.CartRequestDto;
import cartservice.dtos.CartResposneDtos;
import cartservice.dtos.ProductResponseDto;
import cartservice.entity.CartItems;
import cartservice.entity.CartStatus;
import cartservice.entity.Carts;
import cartservice.expcetions.expectionsfiles.OutOfStockProduct;
import cartservice.expcetions.expectionsfiles.UserNotExistsException;
import cartservice.mapper.CartMapper;
import cartservice.mapper.Mapper;
import cartservice.repository.CartItemsRepository;
import cartservice.repository.CartRepository;
import cartservice.repository.ProductRepository;
import cartservice.expcetions.expectionsfiles.CartNotFoundException;
import cartservice.expcetions.expectionsfiles.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements IcartServices {

    private final CartRepository cartRepository;
    private final ProductServiceClient productServiceClient;
    private final ProductRepository productRepository;
    private final CartItemsRepository cartItemsRepository;
    private final CallingUserService callingUserService;

    public CartServiceImpl(CartRepository cartRepository, ProductServiceClient productServiceClient,
                           ProductRepository productRepository,
                           CartItemsRepository cartItemsRepository, CallingUserService callingUserService) {
        this.cartRepository = cartRepository;
        this.productServiceClient = productServiceClient;
        this.productRepository = productRepository;
        this.cartItemsRepository = cartItemsRepository;
        this.callingUserService = callingUserService;
    }

    @Override
    public CartResposneDtos addItemToCart(String email, CartRequestDto dto) throws CartNotFoundException {
        UserResponseDto responseDto1=fetchUserDataAndValidate(email);
        if(responseDto1==null){
            throw new UserNotExistsException(" PLEASE SIGN UP "+email+" NOT EXITS");
        }


Optional<Carts>existingEmail=cartRepository.findByEmail(email);
if(existingEmail.isPresent()){
    Carts carts=existingEmail.get();
    carts.setCartStatus(CartStatus.IN_PROGRESS);
    return CartMapper.fromCart(existingEmail.get());
}
        int intStok=0;
        Carts carts = new Carts();
        carts.setCartStatus(CartStatus.ACCEPTED);
        carts.setEmail(email);
        carts.setCartCreatedTime(LocalDateTime.now());
        List<CartItems> cartItemsList = new ArrayList<>();
        for (CartItems cartItems : dto.getItem()) {
            Optional<CartItems>existingCartItem=cartItemsRepository.findByProductId(cartItems.getProductId());
            if(existingCartItem.isPresent()){
                cartItems.setQuantity(cartItems.getQuantity());
            }
            ProductResponseDto responseDto =fetchProductandValidate(cartItems.getProductId());

                cartItems.setProductId(responseDto.getId());
                cartItems.setProductName(responseDto.getName());
                cartItems.setQuantity(cartItems.getQuantity());
                cartItems.setPrice(responseDto.getPrice());
                cartItemsList.add(cartItems);
                intStok=responseDto.getStock()-cartItems.getQuantity();
                if(intStok<=0){
                    throw new OutOfStockProduct(responseDto.getName()+" THIS PRODUCT IS OUT OF STOCK ");
                }
        }
        long amount = 0;
        for (int i = 0; i < cartItemsList.size(); i++) {
            amount += cartItemsList.get(i).getPrice() * cartItemsList.get(i).getQuantity();
        }
        carts.setTotal(amount);
        carts.setLeftItemStock(intStok);
        carts.setItems(cartItemsList);
        Carts savedCart = cartRepository.save(carts);
        return CartMapper.fromCart(savedCart);
    }
    private ProductResponseDto fetchProductandValidate(long productId){
        ProductResponseDto responseDto =productServiceClient.fetchProductById(productId);
        if (responseDto == null) {//velidation if product fetched or not
            throw new RuntimeException("Product not found: " + productId);
        }
      return responseDto;
    }
    private UserResponseDto fetchUserDataAndValidate(String email){
        UserResponseDto existingUser =callingUserService.getUser(email); // fetching user from user service
        if (existingUser == null) { //validation for user
            throw new UserNotExistsException(" PLEASE SIGN UP EMAIL " + email+ "NOT EXITS");
        }
        return existingUser;
    }

    @Override
    public CartResposneDtos removeItemFromCart(long cartId, long productId) throws CartNotFoundException {
        Carts cart = cartRepository.findById(cartId).orElseThrow(
                () -> new CartNotFoundException("Cart not found for user: " + cartId));
        List<CartItems> updateCartItems = new ArrayList<>();
        for (CartItems items : cart.getItems()) {
            if (items.getProductId() != productId) {
                updateCartItems.add(items);
            }
        }
        long totalcost = 0;
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
    public CartResposneDtos confirmCart(long userId) {
        Carts cart = cartRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));
        cart.setCartStatus(CartStatus.IN_PROGRESS);

        // Perform confirmation logic, e.g., saving the cart as an order
        // ...
        // Optionally clear the cart
//        cart.getItems().clear();
//        cart.setTotal(0);
//        cartRepository.save(cart);
        return CartMapper.fromCart(cart);

    }



    @Override
    public CartResposneDtos getById(long id) throws CartNotFoundException {
        Carts carts=cartRepository.findById(id).orElseThrow(
                ()->new  CartNotFoundException("NO SUCH CART AVAILABLE PLEASE TRY AGAIN "+id));
        carts.setCartStatus(CartStatus.FORWORD_TO_ORDER_SERVICE);
        return CartMapper.fromCart(carts);
    }

    @Override
    public ProductResponseDto getProductByIds(long id) {//extra method for TESTING PRODUCT
        ProductResponseDto responseDto =productServiceClient.fetchProductById(id);
        if (responseDto == null) {
            throw new ProductNotFoundException("Product not found: " +id);
        }
        return responseDto;
    }

    @Override
    public List<CartItemResponseDto> getAllCartItems() throws CartNotFoundException {
        List<CartItems> items = cartItemsRepository.findAll();
        if (items.size()<=0|| items==null){
            throw new CartNotFoundException("CART NOT FOUND PLEASE ADD TO CART FIRST BY BELOW LINK http://localhost:8085/cart/add");
        }
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
    public List<ProductResponseDto> getAllProducts() {
    List<ProductResponseDto>responseDtos=productServiceClient.fetchProduct();
        return responseDtos;
    }

    @Override
    public boolean deleteCart(long cartId) {
      cartRepository.deleteById(cartId);
        return true;
    }

    @Override
    public UserResponseDto testUser(String email) {
        UserResponseDto responseDto1=fetchUserDataAndValidate(email);
        if(responseDto1==null){
            throw new UserNotExistsException(" PLEASE SIGN UP "+email+" NOT EXITS");
        }
        return responseDto1;
    }


}

