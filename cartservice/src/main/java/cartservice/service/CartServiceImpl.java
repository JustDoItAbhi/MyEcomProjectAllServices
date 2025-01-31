package cartservice.service;

import cartservice.client.CallingUserService;
import cartservice.client.ProductServiceClient;
import cartservice.client.dto.UserResponseDto;
import cartservice.dtos.CartItemResponseDto;
import cartservice.dtos.CartRequestDto;
import cartservice.dtos.CartResposneDtos;
import cartservice.client.dto.ProductResponseDto;
import cartservice.entity.CartItems;
import cartservice.entity.CartStatus;
import cartservice.entity.Carts;
import cartservice.securityconfigrations.expcetions.expectionsfiles.OutOfStockProduct;
import cartservice.securityconfigrations.expcetions.expectionsfiles.UserNotExistsException;
import cartservice.mapper.CartMapper;
import cartservice.mapper.CartItemMapper;
import cartservice.repository.CartItemsRepository;
import cartservice.repository.CartRepository;
import cartservice.repository.ProductRepository;
import cartservice.securityconfigrations.expcetions.expectionsfiles.CartNotFoundException;
import cartservice.securityconfigrations.expcetions.expectionsfiles.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements IcartServices {

    private final CartRepository cartRepository;// CART REPOSITORY DECLARATION
    private final ProductServiceClient productServiceClient;// PRODUCT SERVICE CLINET REPOSITORY DECLARATION
    private final ProductRepository productRepository;// PRODUCT REPOSITORY DECLARATION OPTIONAL
    private final CartItemsRepository cartItemsRepository;// CART ITEAM REPOSITORY DECLARATION
    private final CallingUserService callingUserService; // USER CALLLING  DECLARATION
// DEPENDENCY INJECTION
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
    public CartResposneDtos addItemToCart(String email, CartRequestDto dto) throws CartNotFoundException {// ADD ITEAMS TO CART
        UserResponseDto responseDto1=fetchUserDataAndValidate(email);// FETCHING USER BY EMAIL
        if(!responseDto1.getUserEmail().equals(email)){// VALIDATION OF USER EMAIL
            throw new UserNotExistsException(" PLEASE SIGN UP "+email+" NOT EXITS");
        }
        if(responseDto1==null){// VALIDATION FOR USER IF NULL
            throw new UserNotExistsException(" PLEASE SIGN UP "+email+" NOT EXITS");
        }

Optional<Carts>existingEmail=cartRepository.findByEmail(email);// CALLING CART IF EMAIL ALREADY EXISTS
if(existingEmail.isPresent()){// CART VALIDATION IF CART IS ALREADY PRESENT
    Carts carts=existingEmail.get();
    carts.setCartStatus(CartStatus.IN_PROGRESS);// GIVING STATUS IF CART ALREADY EXISTS
    return CartMapper.fromCart(existingEmail.get());
}
        int intStok=0;// CHECK STOCK VARIABLE
        Carts carts = new Carts();
        carts.setEmail(responseDto1.getUserEmail());
        carts.setCartStatus(CartStatus.ACCEPTED);
        carts.setEmail(email);
        carts.setCartCreatedTime(LocalDateTime.now());
        List<CartItems> cartItemsList = new ArrayList<>();
        for (CartItems cartItems : dto.getItem()) {// ADDING TO CART ALL GIVEN CART ITEMS
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
                if(intStok<=0){// IF STOCK IS LESS THEN 0 THEN RETURN ERROR
                    throw new OutOfStockProduct(responseDto.getName()+" THIS PRODUCT IS OUT OF STOCK ");
                }
        }
        long amount = 0;
        for (int i = 0; i < cartItemsList.size(); i++) {// CALCULATE THE TOTAL COST OF SELECTED ITEAMS
            amount += cartItemsList.get(i).getPrice() * cartItemsList.get(i).getQuantity();
        }
        carts.setTotal(amount);
        carts.setLeftItemStock(intStok);
        carts.setItems(cartItemsList);
        Carts savedCart = cartRepository.save(carts);// SAVE TO DATABASE
        return CartMapper.fromCart(savedCart);
    }

    private ProductResponseDto fetchProductandValidate(long productId){// SEPRATE METHOD TO FETCH PRODUCT TO REDUCE OVER LOADING ADDITEMTOCART METHOD
        ProductResponseDto responseDto =productServiceClient.fetchProductById(productId);
        if (responseDto == null) {//velidation if product fetched or not
            throw new RuntimeException("Product not found: " + productId);
        }
      return responseDto;
    }

    private UserResponseDto fetchUserDataAndValidate(String email){// SEPRATE METHOD TO FETCH USER TO REDUCE OVER LOADING ADDITEMTOCART METHOD
        UserResponseDto existingUser =callingUserService.getUser(email); // fetching user from user service
        if (existingUser == null) { //validation for user
            throw new UserNotExistsException(" PLEASE SIGN UP EMAIL " + email+ "NOT EXITS");// THROW ERROR IS USER IS NOT VALID
        }
        return existingUser;
    }



    @Override// remove iteam from cart list
    public CartResposneDtos removeItemFromCart(long cartId, long productId) throws CartNotFoundException {// REMOVE ITEAM FROM CART IF USER WANT TO DELETE ANY CART ITEAM AFTER SELECTION
        Carts cart = cartRepository.findById(cartId).orElseThrow(// VALIDATION
                () -> new CartNotFoundException("Cart not found for user: " + cartId));
        List<CartItems> updateCartItems = new ArrayList<>();
        for (CartItems items : cart.getItems()) {
            if (items.getProductId() != productId) {// LOGIC TO CHECK IF PRODUCT IS SAME AS GIVEN
                updateCartItems.add(items);
            }
        }
        long totalcost = 0;
        int n = updateCartItems.size();
        for (int i = 0; i < n; i++) {// REDUCE THE COST IF ITEAM SUCCESFULLY DELETED
            totalcost += updateCartItems.get(i).getQuantity() * updateCartItems.get(i).getPrice();
        }
        cart.setTotal(totalcost);// SAVE NEW COST
        if (updateCartItems.size() == cart.getItems().size()) {// IF NOT DELETED THEN CHECK IF THE SIZE OF CART STILL SAME
            throw new RuntimeException("CART ID NOT FOUND " + productId);// IF YES THEN THROW ERROR
        }
        cart.setItems(updateCartItems);
        cartRepository.save(cart);// STORE UPDATED CART IN DATABASE
        cartItemsRepository.deleteById(productId);// DELETE PRODUCT ID

        return CartMapper.fromCart(cart);
    }

    @Override
    public CartResposneDtos confirmCart(long userId) {// OPTIONAL CONFIM CART ONCE IF WANT TO RECHECK
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
    public CartResposneDtos getById(long id) throws CartNotFoundException {// GET CART BY CART ID
        Carts carts=cartRepository.findById(id).orElseThrow(// CART VALIDATIONS
                ()->new  CartNotFoundException("NO SUCH CART AVAILABLE PLEASE TRY AGAIN "+id));
        carts.setCartStatus(CartStatus.FORWORD_TO_ORDER_SERVICE);// CHANFE STATUS THAT ORDER IS READY TO PLACE
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
    public List<CartItemResponseDto> getAllCartItems() throws CartNotFoundException {// GET ALL CART ITEAMS
        List<CartItems> items = cartItemsRepository.findAll();
        if (items.size()<=0|| items==null){// VALIDATIONS
            throw new CartNotFoundException("CART NOT FOUND PLEASE ADD TO CART FIRST BY BELOW LINK http://localhost:8085/cart/add");
        }
        List<CartItemResponseDto> dtos = new ArrayList<>();
        for (CartItems cartItems : items) {
            dtos.add(CartItemMapper.fromcartItems(cartItems));
        }
        return dtos;
    }
    @Override
    public boolean deleteCart(long cartId) {// DELETE CART
        cartRepository.deleteById(cartId);
        return true;
    }

    @Override
    public CartItemResponseDto getCartItemById(String userId) {
        return null;
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
//    List<ProductResponseDto>responseDtos=productServiceClient.fetchProduct();
        return null;
    }



    @Override
    public UserResponseDto testUser(String email) {// TESTING USER
        UserResponseDto responseDto1=fetchUserDataAndValidate(email);
        if(responseDto1==null){
            throw new UserNotExistsException(" PLEASE SIGN UP "+email+" NOT EXITS");
        }
        return responseDto1;
    }


}

