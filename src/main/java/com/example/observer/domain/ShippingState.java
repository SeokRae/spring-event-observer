package com.example.observer.domain;

import com.example.observer.event.OrderStatusChangedEvent;

public class ShippingState implements OrderState {
    @Override
    public void handleOrder(OrderContext context) {
        // 배송 준비 로직 처리
        System.out.println("Handling order in SHIPPING state.");

        // 배송 준비 완료 이벤트 발행
        context.getEventPublisher().publishEvent(new OrderStatusChangedEvent(this, "ORDER_ID", OrderStatus.SHIPPING, OrderStatus.DELIVERED));

        // 다음 상태로 전환
        context.setState(new DeliveredState());
    }

    @Override
    public OrderStatus getStatus() {
        return OrderStatus.SHIPPING;
    }
}