package deliveryservice.deliveryservice.service;

import deliveryservice.deliveryservice.dto.CartResposneDtos;
import deliveryservice.deliveryservice.dto.DeliveryResponseDto;
import deliveryservice.deliveryservice.entity.Delivery;
import deliveryservice.deliveryservice.repository.DeliveryRespository;
import deliveryservice.deliveryservice.repository.UserAddressRepository;
import deliveryservice.deliveryservice.template.CallingCartdata;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRespository deliveryRespository;
    private final UserAddressRepository userAddressRepository;
    private CallingCartdata callingCartdata;

    public DeliveryServiceImpl(DeliveryRespository deliveryRespository, UserAddressRepository userAddressRepository, CallingCartdata callingCartdata) {
        this.deliveryRespository = deliveryRespository;
        this.userAddressRepository = userAddressRepository;
        this.callingCartdata = callingCartdata;
    }

    @Override
    public Delivery getNotification(DeliveryResponseDto dto) {
        Delivery delivery=new Delivery();
        delivery.setId(dto.getDeliveryId());
        delivery.setAmount(dto.getAmount());
        delivery.setOrderId(dto.getOrderId());
        delivery.setPaymentStatus(dto.getPaymentStatus());
        deliveryRespository.save(delivery);
        return delivery;
    }

    @Override
    public List<Delivery> getsll() {
        List<Delivery> responseDtos=deliveryRespository.findAll();
        return responseDtos;
    }

    @Override
    public CartResposneDtos fetchCart(long cartId) {
        CartResposneDtos dtos=callingCartdata.fetchingFromCartServcie(cartId);
        if(dtos==null){
            throw new RuntimeException("PROBLEM WITH CART DATA NOT FETHCED "+ cartId);
        }
        return dtos;
    }


}
