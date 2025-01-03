package deliveryservice.deliveryservice.mapper;

import deliveryservice.deliveryservice.dto.DeliveryResponseDto;
import deliveryservice.deliveryservice.dto.DeliveryServiceNotification;

public class DeliveryMapper {
    public static DeliveryResponseDto fromPaymentEntity(DeliveryServiceNotification notification){
        DeliveryResponseDto dto=new DeliveryResponseDto();
        dto.setOrderId(notification.getOrderId());
        dto.setAmount(notification.getAmount());
        dto.setPaymentStatus(notification.getPaymentStatus());
        return dto;
    }
}
