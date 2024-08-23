package com.example.observer.domain;

import com.example.observer.event.OrderStatusChangedEvent;

public class InitialOrderState implements OrderState {
    @Override
    public void handleOrder(OrderContext context) {
        // 주문 생성 로직 처리
        System.out.println("Handling order in INITIAL state.");
        
        // 주문 생성 이벤트 발행
        context.getEventPublisher().publishEvent(new OrderStatusChangedEvent(this, "ORDER_ID", OrderStatus.INITIAL, OrderStatus.ORDER_CREATED));

        // 다음 상태로 전환
        context.setState(new OrderCreatedState());
    }

    @Override
    public OrderStatus getStatus() {
        return OrderStatus.INITIAL;
    }
}