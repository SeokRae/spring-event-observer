package com.example.observer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ShippingService {

  public void prepareShipment(String orderId) {
    // 배송 준비 (물류 시스템 연계)
    log.info("[배송 준비] 배송 준비 중: {}", orderId);
  }

  public void startShipment(String orderId) {
    // 배송 시작
    log.info("[배송 시작] 배송 시작: {}", orderId);
  }
}
