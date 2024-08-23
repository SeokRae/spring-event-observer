package com.example.observer.domain;

import com.example.observer.event.OrderStatusChangedEvent;

public class OrderCreatedState implements OrderState {
    @Override
    public void handleOrder(OrderContext context) {
        // 결제 처리 로직
        System.out.println("Handling order in ORDER_CREATED state.");

        // 결제 완료 이벤트 발행
        context.getEventPublisher().publishEvent(new OrderStatusChangedEvent(this, "ORDER_ID", OrderStatus.ORDER_CREATED, OrderStatus.PAYMENT_COMPLETED));

        // 다음 상태로 전환
        context.setState(new PaymentCompletedState());
    }

    @Override
    public OrderStatus getStatus() {
        return OrderStatus.ORDER_CREATED;
    }
}