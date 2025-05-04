package com.likelion12th.shop.service;

import com.likelion12th.shop.dto.Order.OrderDto;
import com.likelion12th.shop.dto.Order.OrderItemDto;
import com.likelion12th.shop.dto.Order.OrderReqDto;
import com.likelion12th.shop.entity.Item;
import com.likelion12th.shop.entity.Member;
import com.likelion12th.shop.entity.Order;
import com.likelion12th.shop.entity.OrderItem;
import com.likelion12th.shop.repository.ItemRepository;
import com.likelion12th.shop.repository.MemberRepository;
import com.likelion12th.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    public Long createNewOrder(OrderReqDto orderReqDto, String email) {

        Member member = memberRepository.findByEmail(email); // 이메일로 회원 조회
        if (member == null) { // 회원이 존재하지 않을 경우 email로 새로운 회원 생성
            member = new Member();
            member.setEmail(email);
            memberRepository.save(member);
        }

        Long itemId = orderReqDto.getItemId(); // OrderReqDto에서 상품 ID를 가져온다

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

        OrderItem orderItem = OrderItem.createOrderItem(item, orderReqDto.getCount());

        Order order = Order.createOrder(member, Collections.singletonList(orderItem));

        orderRepository.save(order); //주문 저장

        return order.getId(); //주문 ID 반환
    }

    // 모든 주문 내역 조회
    public List<OrderDto> getAllOrderByUserEmail(String email) {
        List<Order> orders = orderRepository.findByMemberEmail(email);

        List<OrderDto> orderDtos = new ArrayList<>();

        orders.forEach(order -> orderDtos.add(new OrderDto().of(order)));

        return orderDtos;
    }

    // 주문내역 상세조회
    public OrderItemDto getOrderDtails(Long orderId, String email){

        Order order = orderRepository.findById(orderId)  //ordeId로 주문 조회
                .orElseThrow(() -> new IllegalArgumentException("주문 ID 없음 : " + orderId));

        // 주문을 생성한 사용자인지 확인
        if(!order.getMember().getEmail().equals(email)){
            throw new IllegalArgumentException("주문자만 접근 가능함");
        }
        // 주문에 속한 주문 상품들을 가져온다
        List<OrderItem> orderItems = order.getOrderItemList();;

        if(!orderItems.isEmpty()){
            OrderItem orderItem = orderItems.get(0);

            return OrderItemDto.of(orderItem);
        }else{
            throw new IllegalArgumentException("주문 아이템이 없음");
        }
    }

    // 주문 취소
    public void cancelOrder(Long orderId, String email) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문 ID 없음 : " + orderId));

        // 주문을 생성한 사용자인지 확인
        if(!order.getMember().getEmail().equals(email)){
            throw new IllegalArgumentException("취소 권한이 없음");
        }

        order.cancelOrder();   // 주문 상태를 "CANCEL"로 변경
        orderRepository.save(order);
    }

}


