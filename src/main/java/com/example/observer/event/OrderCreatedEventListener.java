package com.example.observer.event;

import com.example.observer.service.ShippingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreatedEventListener {

  private final ShippingService shippingService;
  private final ApplicationEventPublisher eventPublisher;

  /* 이벤트라는 것 자체는 비동기가 아니기 때문에 비동기 설정도 필요 */
  @Async
  @EventListener
  public void handleOrderCreatedEvent(OrderCreatedEvent event) {
    String orderId = event.getOrderId();
    // 배송 준비 로직
    shippingService.prepareShipment(orderId);

    // 배송 시작 (비동기적으로 처리 가능)
    shippingService.startShipment(orderId);

    // 배송 시작 후 주문 상태를 "배송 중"으로 업데이트하는 이벤트 발행
    eventPublisher.publishEvent(new OrderStatusChangedEvent(this, orderId, "PAYMENT_COMPLETED", "SHIPPING"));
    log.info("[이벤트 발행] 주문 ID: {} 상태를 'SHIPPING'으로 업데이트하는 이벤트 발행", orderId);
  }
}