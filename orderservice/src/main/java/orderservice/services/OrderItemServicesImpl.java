package orderservice.services;

import orderservice.configrations.Incomingcalls;
import orderservice.dtos.CartResposneDtos;
import orderservice.dtos.OrderResponseDto;
import orderservice.entity.OrderStatus;
import orderservice.entity.Orders;
import orderservice.exceptions.OrderCannotPLacedexception;
import orderservice.exceptions.SignUpException;
import orderservice.mappers.OrderMapper;
import orderservice.repositorties.OrderRepository;
import orderservice.templates.UserclientRestTemplate;
import orderservice.users.userdtos.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
public class OrderItemServicesImpl implements OrderItemServices {
    private final OrderRepository orderRepository;
    private final RestTemplateBuilder restTemplateBuilder;
    private final HashMap<Long,Object>ordersMap;
    private final Incomingcalls incomingcalls;
    private final UserclientRestTemplate template;

    public OrderItemServicesImpl(OrderRepository orderRepository,
                                 RestTemplateBuilder restTemplateBuilder, HashMap<Long, Object> ordersMap,
                                 Incomingcalls incomingcalls, UserclientRestTemplate template) {
        this.orderRepository = orderRepository;
        this.restTemplateBuilder = restTemplateBuilder;
        this.ordersMap = ordersMap;
        this.incomingcalls = incomingcalls;
        this.template = template;
    }

    @Override
    public OrderResponseDto getCartItems(String userEmail, long cartId) {
        UserResponseDto dto=template.getUserById(userEmail);
        if(dto==null){
            throw new SignUpException("USER NOT FOUND PLEASE SIGN UP  "+userEmail);
        }
        CartResposneDtos resposneDtos=incomingcalls.fetchProduct(cartId);
        if(resposneDtos==null){
                throw new OrderCannotPLacedexception(" CANNOT FIND CART "+cartId);
        }
        Orders orders= new Orders();
        orders.setUserId(resposneDtos.getUserId());
        orders.setPrice(resposneDtos.getTotal());
        if(resposneDtos.getTotal()<=0.0){
            orders.setOrderStatus(OrderStatus.PENDING);
        }else {
            orders.setOrderStatus(OrderStatus.SUCESSFULL);
            //if order is succefull then redirect to login if cannot login then sign up

        }
        orderRepository.save(orders);
        return OrderMapper.fromEntity(orders);
    }

    @Override
    public boolean deleteOrder(long id) {
        orderRepository.deleteById(id);
        return true;
    }

    @Override
    public Orders getOrderById(long id) {
        Orders existingOrder=orderRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("ID NOT FOUND "+ id));
        if(ordersMap.containsKey(id)){
            return existingOrder;
        }
        return existingOrder;
    }

    @Override
    public String getUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            System.out.println("HERE IS ROLLLLLLLLLLEEEEEEE");
            return jwt.getClaimAsStringList("roles").toString(); // Extract "roles" claim
        }
        return "No roles available";
    }
}
