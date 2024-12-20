package cartservice.service;

import cartservice.client.ProductServiceClient;
import cartservice.dtos.CartRequestDto;
import cartservice.dtos.CartResposneDtos;
import cartservice.dtos.ProductResponseDto;
import cartservice.entity.CartItems;
import cartservice.entity.Carts;
import cartservice.entity.UserDetails;
import cartservice.mapper.CartMapper;
import cartservice.repository.CartItemsRepository;
import cartservice.repository.CartRepository;
import cartservice.repository.UserDetailsReposirtoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService implements IcartServices{

private CartRepository cartRepository;
    private ProductServiceClient productServiceClient;
    private UserDetailsReposirtoy userDetailsReposirtoy;
    private CartItemsRepository cartItemsRepository;
    @Autowired
    @Qualifier("productServiceRestClient")
    private RestClient restClient;

    public CartService(CartRepository cartRepository, ProductServiceClient productServiceClient,
                       UserDetailsReposirtoy userDetailsReposirtoy, CartItemsRepository cartItemsRepository) {
        this.cartRepository = cartRepository;
        this.productServiceClient = productServiceClient;
        this.userDetailsReposirtoy = userDetailsReposirtoy;
        this.cartItemsRepository = cartItemsRepository;
    }
    @Override
        public CartResposneDtos addItemToCart(CartRequestDto dto) {
            // Validate if the user exists
            Optional<UserDetails> existingUser = userDetailsReposirtoy.findByUserEmail(dto.getUserId());
            if (existingUser.isEmpty()) {
                throw new RuntimeException("Please sign in again: " + dto.getUserId());
            }

            Carts carts = new Carts();
            carts.setUserId(dto.getUserId());
            List<CartItems>cartItemsList=new ArrayList<>();
            for (CartItems cartItems : dto.getItem()) {
                ProductResponseDto responseDto = restClient.get()
                        .uri("/" + cartItems.getProductId())
                        .retrieve()
                        .body(ProductResponseDto.class);
                // Blocking call to retrieve the response

                if (responseDto == null) {
                    throw new RuntimeException("Product not found: " + cartItems.getProductId());
                }

                cartItems.setProductId(responseDto.getId());
                cartItems.setProductName(responseDto.getName());
                cartItems.setQuantity(cartItems.getQuantity());
                cartItems.setPrice(responseDto.getPrice());
                cartItemsList.add(cartItems);
            }
            double amount=0.0;
            for(int i=0;i<cartItemsList.size();i++){
                amount+=cartItemsList.get(i).getPrice()*cartItemsList.get(i).getQuantity();
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
        List<CartItems>updateCartItems=new ArrayList<>();
        for(CartItems items:cart.getItems()) {
            if (items.getProductId() != productId) {
                updateCartItems.add(items);
            }
        }
        double totalcost=0.0;
        int n=updateCartItems.size();
        for(int i=0;i<n;i++){
            totalcost+=updateCartItems.get(i).getQuantity()*updateCartItems.get(i).getPrice();
        }
        cart.setTotal(totalcost);
            if(updateCartItems.size()==cart.getItems().size()) {
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
//            CartItems item = productServiceClient.fetchProductById(cartsitems.getProductId());
//            carts.addItem(item);
//        }
//        Carts savedCart = cartRepository.save(carts);
        return null;
    }
    @Override
    public ProductResponseDto getByIds(long id) {
    ProductResponseDto responseDto=restClient.get()
            .uri("/"+id)
            .retrieve()
            .body(ProductResponseDto.class);

        return responseDto;
    }
}
