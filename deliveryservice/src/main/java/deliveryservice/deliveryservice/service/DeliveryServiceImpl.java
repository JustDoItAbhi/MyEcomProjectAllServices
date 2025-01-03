package deliveryservice.deliveryservice.service;

import deliveryservice.deliveryservice.dto.DeliveryResponseDto;
import deliveryservice.deliveryservice.entity.Delivery;
import deliveryservice.deliveryservice.repository.DeliveryRespository;
import deliveryservice.deliveryservice.repository.UserAddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRespository deliveryRespository;
    private final UserAddressRepository userAddressRepository;

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

    @Override
    public List<Delivery> getsll() {
        List<Delivery> responseDtos=deliveryRespository.findAll();
        return responseDtos;
    }


}
