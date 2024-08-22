package com.example.observer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class InventoryService {

  private final Map<String, Integer> inventory = new ConcurrentHashMap<>();

  // 재고 초기화
  public InventoryService() {
    inventory.put("prod_1", 100); // 예시: 상품 ID 1번에 대한 초기 재고는 100개
    inventory.put("prod_2", 50);  // 예시: 상품 ID 2번에 대한 초기 재고는 50개
  }

  public synchronized boolean deductStock(String productId, int quantity) {
    Integer currentStock = inventory.get(productId);
    if (currentStock == null || currentStock < quantity) {
      log.info("[재고 차감] 재고 부족: 상품 ID: {}, 현재 재고: {}, 차감 요청 수량: {}", productId, currentStock, quantity);
      return false; // 재고 부족
    }
    inventory.put(productId, currentStock - quantity);
    return true;
  }

  public int getStock(String productId) {
    return inventory.getOrDefault(productId, 0);
  }
}