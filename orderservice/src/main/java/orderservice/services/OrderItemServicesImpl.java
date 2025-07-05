package orderservice.services;

import orderservice.dtos.CheckOutOrder;
import orderservice.entity.UserDetails;
import orderservice.exceptions.SignUpException;
import orderservice.templatesservice.RestTemplateService;
import orderservice.dtos.CartResposneDtos;
import orderservice.dtos.OrderResponseDto;
import orderservice.entity.OrderStatus;
import orderservice.entity.Orders;
import orderservice.exceptions.OrderCannotPLacedexception;
import orderservice.mappers.OrderMapper;
import orderservice.repositorties.OrderRepository;
import orderservice.users.userdtos.UserResponseDto;
import orderservice.users.usermapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class OrderItemServicesImpl implements OrderItemServices {// IMPLEMENTATION OF ORDER SERVICE
    private final OrderRepository orderRepository;// DECLARATION OF ORDER REPOSITORY
    private final HashMap<Long,Object>ordersMap;
    @Autowired
    private RedisTemplate<String,CheckOutOrder>redisTemplate;
    private final RestTemplateService restTemplateService;// // DECLARATION OF REST TEMPLATE SERVICE
// DEPENDENCY INJECTION
    public OrderItemServicesImpl(OrderRepository orderRepository, HashMap<Long, Object> ordersMap, RestTemplateService restTemplateService) {
        this.orderRepository = orderRepository;
        this.ordersMap = new HashMap<>();
        this.restTemplateService = restTemplateService;
    }

    @Override
    public OrderResponseDto getCartItems(long cartId) {// GET CART ITEMS
        CartResposneDtos resposneDtos=restTemplateService.fetchProduct(cartId);// FETCH CART BY CART ID
        if(resposneDtos==null){// VART VALIDATION
                throw new OrderCannotPLacedexception(" CANNOT FIND CART "+cartId);
        }
        Orders orders= new Orders();
        orders.setCartId(resposneDtos.getCartId());
        orders.setPrice(resposneDtos.getTotal());
        if(resposneDtos.getTotal()<=0.0){// IF PRICE OF ALL ITEMS IS LESS THEN OR EQUALS TO ZERO THEN GIVE STATUS PENDING ORDER
            orders.setOrderStatus(OrderStatus.PENDING);
        }else {// OTHERWISE  GIVE SUCCESFULL ORDER
            orders.setOrderStatus(OrderStatus.SUCESSFULL);
        }
        orderRepository.save(orders);// SAVE ORDER TO DATABASE
        OrderResponseDto responseDto=OrderMapper.fromEntity(orders);
//        ordersMap.put(cartId,responseDto);
        return OrderMapper.fromEntity(orders);
    }

    @Override
    public boolean deleteOrder(long id) {// DELETE ORDER
        orderRepository.deleteById(id);
        return true;
    }

    @Override
    public CheckOutOrder userLoginOrSignUp(long cartiD, String email) {
        CheckOutOrder check= (CheckOutOrder) redisTemplate.opsForValue().get(email);
        if(check!=null){
            return check;
        }
         OrderResponseDto orderResponseDto=getCartItems(cartiD);

        UserResponseDto dto=restTemplateService.getUserById(email);
        if(dto==null){
            throw new SignUpException("please sign up "+ "http://localhost:8090/user/signup");
        }
      check= UserMapper.fromUserResponse(dto,orderResponseDto);
              redisTemplate.opsForValue().set(email,check);
        getOrderById(orderResponseDto.getOrderid());

        return check;
    }

    @Override
    public Orders getOrderById(long id) {// GET ORDER BY ID by hashmap

        Orders existingOrder=orderRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("ID NOT FOUND "+ id));// CHECK STATUS IF READY TO PAY
        existingOrder.setOrderStatus(OrderStatus.READY_TO_PAY);
        if(ordersMap.containsKey(id)){
            return existingOrder;
        }
        return existingOrder;
    }

    @Override
    public String getUserRoles() {// OPTIONAL USER ROLE CHECK
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            System.out.println("HERE IS ROLLLLLLLLLLEEEEEEE");
            return jwt.getClaimAsStringList("roles").toString(); // Extract "roles" claim
        }
        return "No roles available";
    }

    @Override
    public CheckOutOrder byEmail(String email) {
        CheckOutOrder check=  redisTemplate.opsForValue().get(email);
        if(check==null){
            throw new SignUpException("user not found "+ email);
        }
        return check;
    }
}
