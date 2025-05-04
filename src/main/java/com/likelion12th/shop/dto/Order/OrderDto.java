package com.likelion12th.shop.dto.Order;


import com.likelion12th.shop.constant.OrderStatus;
import com.likelion12th.shop.entity.Order;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter @Setter
@NoArgsConstructor
public class OrderDto {
    private Long orderId;
    private String orderDate;
    private OrderStatus orderStatus;
    private Long itemId;
    private int totalPrice;

    private static ModelMapper modelMapper = new ModelMapper();

    // Order 객체를 OrderDto로 변환
    public OrderDto of(Order order) {
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        if(!order.getOrderItemList().isEmpty()){
            orderDto.setItemId(order.getOrderItemList().get(0).getItem().getId());
        }
        return orderDto;
    }

}
