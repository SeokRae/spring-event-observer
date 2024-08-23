package com.example.observer.domain;

import com.example.observer.event.OrderStatusChangedEvent;

public class DeliveredState implements OrderState {
  @Override
  public void handleOrder(OrderContext context) {
    // 배송 완료 로직 처리
    System.out.println("Handling order in DELIVERED state.");

    // 배송 완료 이벤트 발행
    context.getEventPublisher().publishEvent(new OrderStatusChangedEvent(this, "ORDER_ID", OrderStatus.DELIVERED, OrderStatus.COMPLETED));

    // 주문 완료 상태로 전환 (만약 있다면)
    // context.setState(new CompletedState());
  }

  @Override
  public OrderStatus getStatus() {
    return OrderStatus.DELIVERED;
  }
}