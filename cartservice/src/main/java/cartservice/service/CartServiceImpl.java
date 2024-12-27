package cartservice.service;

import cartservice.client.ProductServiceClient;
import cartservice.dtos.CartItemResponseDto;
import cartservice.dtos.CartRequestDto;
import cartservice.dtos.CartResposneDtos;
import cartservice.dtos.ProductResponseDto;
import cartservice.entity.CartItems;
import cartservice.entity.Carts;
import cartservice.mapper.CartMapper;
import cartservice.mapper.Mapper;
import cartservice.repository.CartItemsRepository;
import cartservice.repository.CartRepository;
import cartservice.repository.ProductRepository;
import expcetions.CartNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements IcartServices {

    private final CartRepository cartRepository;
    private final ProductServiceClient productServiceClient;
    private final ProductRepository productRepository;
    private final CartItemsRepository cartItemsRepository;

    public CartServiceImpl(CartRepository cartRepository, ProductServiceClient productServiceClient,
                           ProductRepository productRepository, CartItemsRepository cartItemsRepository) {
        this.cartRepository = cartRepository;
        this.productServiceClient = productServiceClient;
        this.productRepository = productRepository;
        this.cartItemsRepository = cartItemsRepository;
    }

    @Override
    public CartResposneDtos addItemToCart(CartRequestDto dto) {
        Carts carts = new Carts();
        carts.setCartCreatedTime(LocalDateTime.now());
        List<CartItems> cartItemsList = new ArrayList<>();
        for (CartItems cartItems : dto.getItem()) {
            ProductResponseDto responseDto =productServiceClient.fetchProductById(cartItems.getProductId());
            if (responseDto == null) {
                throw new RuntimeException("Product not found: " + cartItems.getProductId());
            }
            cartItems.setProductId(responseDto.getId());
            cartItems.setProductName(responseDto.getName());
            cartItems.setQuantity(cartItems.getQuantity());
            cartItems.setPrice(responseDto.getPrice());
            cartItemsList.add(cartItems);
        }
        long amount = 0;
        for (int i = 0; i < cartItemsList.size(); i++) {
            amount += cartItemsList.get(i).getPrice() * cartItemsList.get(i).getQuantity();
        }
        carts.setTotal(amount);
        carts.setItems(cartItemsList);
        Carts savedCart = cartRepository.save(carts);
        return CartMapper.fromCart(savedCart);
    }

    @Override
    public CartResposneDtos removeItemFromCart(long userId, long productId) {
        Carts cart = cartRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("Cart not found for user: " + userId));
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

        return CartMapper.fromCart(carts);
    }

    @Override
    public ProductResponseDto getByIds(long id) {//extra method for practice
        ProductResponseDto responseDto =productServiceClient.fetchProductById(id);
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
    public List<ProductResponseDto> getAllProducts() {
    List<ProductResponseDto>responseDtos=productServiceClient.fetchProduct();
        return responseDtos;
    }

    @Override
    public boolean deleteCart(long cartId) {
        cartRepository.deleteById(cartId);
        return true;
    }


}

