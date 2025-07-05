package deliveryservice.deliveryservice.servicesproject.mapper;

import deliveryservice.deliveryservice.servicesproject.dtos.DeliveryResponseDto;
import deliveryservice.deliveryservice.servicesproject.dtos.DeliveryServiceNotification;

public class DeliveryMapper {
    public static DeliveryResponseDto fromPaymentEntity(DeliveryServiceNotification notification){// MAPPING FROM ENTITY TO RESPONSE DTO
        DeliveryResponseDto dto=new DeliveryResponseDto();
        dto.setOrderId(notification.getOrderId());
        dto.setAmount(notification.getAmount());
        dto.setPaymentStatus(notification.getPaymentStatus());
        return dto;
    }
}
