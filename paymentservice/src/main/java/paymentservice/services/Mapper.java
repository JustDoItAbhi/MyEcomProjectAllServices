package paymentservice.services;

import org.aspectj.weaver.ast.Or;
import paymentservice.dtos.OrderResponseDto;
import paymentservice.entity.OrderPayment;

public class Mapper {
    public static OrderPayment fromDto(OrderResponseDto dto){
        OrderPayment orderPayment=new OrderPayment();
        orderPayment.setOrderId(dto.getId());
        orderPayment.setOrderStatus(dto.getOrderStatus());
        orderPayment.setPrice(dto.getPrice());
//        orderPayment.setCreatedAt(dto.getCreatedAt());
        orderPayment.setUserId(dto.getUserId());
        return orderPayment;
    }
}
