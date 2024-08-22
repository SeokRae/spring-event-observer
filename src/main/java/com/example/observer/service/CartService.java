package com.example.observer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class CartService {

  private final Map<String, Integer> cartItems = new ConcurrentHashMap<>(); // 상품 ID와 수량을 저장

  public void addItemToCart(String productId, Integer quantity) {
    // 상품 ID와 수량을 저장
    cartItems.merge(productId, quantity, Integer::sum);
    log.info("[장바구니 추가] 장바구니 상품: {}", cartItems);
  }

  public Map<String, Integer> getCartItems() {
    // 장바구니에 담긴 상품 ID와 수량을 반환
    return new HashMap<>(cartItems);
  }

  public void clearCart() {
    log.info("[장바구니 비우기]");
    // 장바구니 비우기
    cartItems.clear();
  }
}
