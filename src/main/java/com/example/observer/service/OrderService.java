package com.example.observer.service;

import com.example.observer.event.OrderCreatedEvent;
import com.example.observer.event.OrderStatusChangedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

  private final ApplicationEventPublisher eventPublisher;
  private final CartService cartService;
  private final InventoryService inventoryService;
  private final PaymentService paymentService;

  @Transactional
  public void createOrder(String orderId) {
    String oldStatus = "INITIAL";
    String newStatus = "ORDER_CREATED";

    log.info("[주문 생성] 시작: {}", orderId);

    // 주문 상태 변경 전 비동기 이력 기록 이벤트 발행
    eventPublisher.publishEvent(new OrderStatusChangedEvent(this, orderId, oldStatus, newStatus));

    // 1. 카트에서 아이템 가져오기
    Map<String, Integer> cartItems = cartService.getCartItems();
    if (cartItems.isEmpty()) {
      throw new IllegalStateException("Cart is empty!");
    }

    log.info("[주문 생성] 장바구니 상품: {}", cartItems);

    // 2. 결제 처리
    boolean paymentSuccess = paymentService.processPayment(orderId);
    if (!paymentSuccess) {
      log.warn("[결제 실패] 주문 ID: {}", orderId);
      throw new IllegalStateException("Payment failed for order ID: " + orderId);
    }

    // 3. 재고 차감
    for (Map.Entry<String, Integer> entry : cartItems.entrySet()) {
      String productId = entry.getKey();
      Integer quantity = entry.getValue();

      int availableStock = inventoryService.getStock(productId);
      if (availableStock < quantity) {
        log.warn("[주문 생성] 재고 부족 상품 ID: {}, 요청 수량: {}, 남은 재고: {}", productId, quantity, availableStock);
        throw new IllegalStateException("[재고 부족] 상품 아이디: " + productId);
      }

      boolean stockDeducted = inventoryService.deductStock(productId, quantity);
      if (!stockDeducted) {
        log.warn("[주문 생성] 재고 차감 실패 상품 ID: {}, 수량: {}", productId, quantity);
        throw new IllegalStateException("[재고 차감 실패] 상품 아이디: " + productId);
      }

      log.info("[주문 생성] 재고 차감 성공 상품 ID: {}, 차감 수량: {}", productId, quantity);
    }

    // 주문 상태 변경
    oldStatus = newStatus;
    newStatus = "PAYMENT_COMPLETED";

    // 결제 완료 후 상태 변경 이력 기록 이벤트 발행
    eventPublisher.publishEvent(new OrderStatusChangedEvent(this, orderId, oldStatus, newStatus));


    // 4. 비동기적으로 처리될 이벤트 발행 (배송 준비 및 통계 업데이트)
    OrderCreatedEvent event = new OrderCreatedEvent(this, orderId);
    log.info("[주문 생성] 주문 생성 후 비동기 이벤트 발행");
    eventPublisher.publishEvent(event);

    // 5. 카트 비우기
    cartService.clearCart();

    log.info("[주문 생성] ====================================== 주문 생성 완료");
  }
}