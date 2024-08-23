package com.example.observer.service;

import com.example.observer.domain.OrderStatus;
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
    OrderStatus currentStatus = OrderStatus.INITIAL;

    try {
      log.info("[주문 생성] 시작: {}", orderId);

      // 1. 주문 생성 단계
      currentStatus = transitionStatus(orderId, OrderStatus.INITIAL, OrderStatus.ORDER_CREATED);

      // 2. 장바구니 확인
      Map<String, Integer> cartItems = verifyAndGetCartItems();

      // 3. 결제 처리
      processPayment(orderId);

      // 4. 재고 확인 및 차감
      deductInventory(cartItems);

      // 5. 배송 준비 단계로 상태 전환
      currentStatus = transitionStatus(orderId, OrderStatus.PAYMENT_COMPLETED, OrderStatus.SHIPPING);

      // 6. 비동기 이벤트 발행
      publishOrderCreatedEvent(orderId);

      // 7. 카트 비우기
      clearCart();

      log.info("[주문 생성] ====================================== 주문 생성 완료");

    } catch (Exception e) {
      handleOrderFailure(orderId, currentStatus, e);
      throw e;  // 예외 재발행
    }
  }

  private Map<String, Integer> verifyAndGetCartItems() {
    Map<String, Integer> cartItems = cartService.getCartItems();
    if (cartItems.isEmpty()) {
      throw new IllegalStateException("Cart is empty!");
    }
    log.info("[주문 생성] 장바구니 상품: {}", cartItems);
    return cartItems;
  }

  private void processPayment(String orderId) {
    if (!paymentService.processPayment(orderId)) {
      log.warn("[결제 실패] 주문 ID: {}", orderId);
      throw new IllegalStateException("Payment failed for order ID: " + orderId);
    }
  }

  private void deductInventory(Map<String, Integer> cartItems) {
    for (Map.Entry<String, Integer> entry : cartItems.entrySet()) {
      String productId = entry.getKey();
      Integer quantity = entry.getValue();

      int availableStock = inventoryService.getStock(productId);
      if (availableStock < quantity) {
        log.warn("[주문 생성] 재고 부족 상품 ID: {}, 요청 수량: {}, 남은 재고: {}", productId, quantity, availableStock);
        throw new IllegalStateException("[재고 부족] 상품 아이디: " + productId);
      }

      if (!inventoryService.deductStock(productId, quantity)) {
        log.warn("[주문 생성] 재고 차감 실패 상품 ID: {}, 수량: {}", productId, quantity);
        throw new IllegalStateException("[재고 차감 실패] 상품 아이디: " + productId);
      }

      log.info("[주문 생성] 재고 차감 성공 상품 ID: {}, 차감 수량: {}", productId, quantity);
    }
  }

  private OrderStatus transitionStatus(String orderId, OrderStatus oldStatus, OrderStatus newStatus) {
    log.info("[주문 상태 전환] {} -> {} (주문 ID: {})", oldStatus, newStatus, orderId);
    eventPublisher.publishEvent(new OrderStatusChangedEvent(this, orderId, oldStatus, newStatus));
    return newStatus;
  }

  private void publishOrderCreatedEvent(String orderId) {
    OrderCreatedEvent event = new OrderCreatedEvent(this, orderId);
    log.info("[주문 생성] 주문 생성 후 비동기 이벤트 발행");
    eventPublisher.publishEvent(event);
  }

  private void clearCart() {
    cartService.clearCart();
    log.info("[주문 생성] 장바구니 비우기 완료");
  }

  private void handleOrderFailure(String orderId, OrderStatus currentStatus, Exception e) {
    log.error("[주문 생성] 오류 발생: {}", e.getMessage());
    transitionStatus(orderId, currentStatus, OrderStatus.CANCELLED);
  }
}