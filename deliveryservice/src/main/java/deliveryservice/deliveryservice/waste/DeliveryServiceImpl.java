package deliveryservice.deliveryservice.waste;

import deliveryservice.deliveryservice.servicesproject.dto.DeliveryResponseDto;
import deliveryservice.deliveryservice.servicesproject.entity.Delivery;
import deliveryservice.deliveryservice.servicesproject.repository.DeliveryRespository;
import deliveryservice.deliveryservice.servicesproject.repository.UserAddressRepository;

import org.springframework.stereotype.Service;

@Service
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRespository deliveryRespository;
    private final UserAddressRepository userAddressRepository;
//    private CallingCartdata callingCartdata;


    public DeliveryServiceImpl(DeliveryRespository deliveryRespository, UserAddressRepository userAddressRepository) {
        this.deliveryRespository = deliveryRespository;
        this.userAddressRepository = userAddressRepository;
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
//
//    @Override
//    public List<Delivery> getsll() {
//        List<Delivery> responseDtos=deliveryRespository.findAll();
//        return responseDtos;
//    }

//    @Override
//    public CartResposneDtos fetchCart(long cartId) {
//        CartResposneDtos dtos=callingCartdata.fetchingFromCartServcie(cartId);
//        if(dtos==null){
//            throw new RuntimeException("PROBLEM WITH CART DATA NOT FETHCED "+ cartId);
//        }
//        return dtos;
//    }


}
