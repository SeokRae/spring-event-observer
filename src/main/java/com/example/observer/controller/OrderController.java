package com.example.observer.controller;

import com.example.observer.controller.request.OrderCreateRequest;
import com.example.observer.controller.response.CreateOrderResponse;
import com.example.observer.service.CartService;
import com.example.observer.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/api")
public class OrderController {


  private final OrderService orderService;
  private final CartService cartService;

  @Autowired
  public OrderController(OrderService orderService, CartService cartService) {
    this.orderService = orderService;
    this.cartService = cartService;
  }

  @PostMapping("/orders")
  public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody OrderCreateRequest request) {
    log.info("[주문 생성] 상품 ID: {}, 수량: {}", request.getProductId(), request.getQuantity());
    // 1. 카트에 상품 추가
    cartService.addItemToCart(request.getProductId(), request.getQuantity());

    // 2. 주문 ID 생성 (예시로 주문 ID를 ord_1로 설정, 실제로는 서비스나 DB를 통해 생성)
    String orderId = "ord_1";

    // 3. 주문 생성 및 이벤트 발행
    orderService.createOrder(orderId);

    // 4. 주문 생성 결과를 응답으로 반환
    CreateOrderResponse response = new CreateOrderResponse(orderId);
    return ResponseEntity.ok(response);
  }
}
