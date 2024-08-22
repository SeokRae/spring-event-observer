package com.example.observer.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderHistoryListener {

  @Async
  @EventListener
  public void handleOrderStatusChangedEvent(OrderStatusChangedEvent event) {
    String orderId = event.getOrderId();
    String oldStatus = event.getOldStatus();
    String newStatus = event.getNewStatus();

    // 주문 상태 업데이트 로직 (예: DB 업데이트)
    updateOrderStatus(orderId, newStatus);

    // 이력 기록 로직 구현 (DB 저장 또는 로그 파일 기록 등)
    saveHistory(orderId, oldStatus, newStatus);
  }

  private void updateOrderStatus(String orderId, String newStatus) {
    // 실제 주문 상태를 업데이트하는 로직 (예: DB 업데이트)
    log.info("[주문 상태 업데이트] 주문 ID: {} 상태를 '{}'로 업데이트", orderId, newStatus);
  }

  private void saveHistory(String orderId, String oldStatus, String newStatus) {
    // 실제 이력 정보를 저장하는 로직 (예: DB에 기록)
    log.info("[이력 저장] 주문 ID: {} 상태 변경: {} -> {}", orderId, oldStatus, newStatus);
  }
}