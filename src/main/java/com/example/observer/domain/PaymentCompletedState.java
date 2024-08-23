package com.example.observer.domain;

import com.example.observer.event.OrderStatusChangedEvent;

public class PaymentCompletedState implements OrderState {
    @Override
    public void handleOrder(OrderContext context) {
        // 재고 차감 및 배송 준비 로직
        System.out.println("Handling order in PAYMENT_COMPLETED state.");

        // 배송 준비 이벤트 발행
        context.getEventPublisher().publishEvent(new OrderStatusChangedEvent(this, "ORDER_ID", OrderStatus.PAYMENT_COMPLETED, OrderStatus.SHIPPING));

        // 다음 상태로 전환
        context.setState(new ShippingState());
    }

    @Override
    public OrderStatus getStatus() {
        return OrderStatus.PAYMENT_COMPLETED;
    }
}