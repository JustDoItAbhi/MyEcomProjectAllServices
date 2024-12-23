package orderservice.services;

import orderservice.configrations.Incomingcalls;
import orderservice.dtos.CartResposneDtos;
import orderservice.dtos.OrderResponseDto;
import orderservice.entity.OrderStatus;
import orderservice.entity.Orders;
import orderservice.mappers.OrderMapper;
import orderservice.repositorties.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
public class OrderItemServicesImpl implements OrderItemServices {
    private OrderRepository orderRepository;
    private RestTemplate restTemplate;
    private HashMap<Long,Object>ordersMap;
    private Incomingcalls incomingcalls;


    public OrderItemServicesImpl(OrderRepository orderRepository, RestTemplate restTemplate, HashMap<Long, Object> ordersMap, Incomingcalls incomingcalls) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
        this.ordersMap = ordersMap;
        this.incomingcalls = incomingcalls;
    }

    @Override
    public OrderResponseDto getCartItems(String userId) {
        CartResposneDtos resposneDtos=incomingcalls.fetchProduct(userId);
        if(resposneDtos==null){
            throw new RuntimeException("NO CART FOUND PLEASE CHECK CONNECTION "+userId);
        }
        Orders orders= new Orders();
        orders.setUserId(resposneDtos.getUserId());
        orders.setPrice(resposneDtos.getTotal());
        if(resposneDtos.getTotal()<=0.0){
            orders.setOrderStatus(OrderStatus.PENDING);
        }
        orders.setOrderStatus(OrderStatus.SUCESSFULL);
        orderRepository.save(orders);
        return OrderMapper.fromEntity(orders);
    }

    @Override
    public boolean deleteOrder(long id) {
        orderRepository.deleteById(id);
        return true;
    }

    @Override
    public Orders getById(long id) {
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
