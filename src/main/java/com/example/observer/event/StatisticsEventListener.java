package com.example.observer.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StatisticsEventListener {

  @Async
  @EventListener
  public void handleOrderCreatedEvent(OrderCreatedEvent event) {
    String orderId = event.getOrderId();

    // 통계 업데이트 로직
    updateStatistics(orderId);
    log.info("[비동기 통계 업데이트 완료] 주문 ID: {}", orderId);
  }

  private void updateStatistics(String orderId) {
    // 통계 업데이트 로직 구현
    log.info("[통계 업데이트 진행 중] 주문 ID: {}", orderId);
  }
}