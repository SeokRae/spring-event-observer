package com.example.observer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentService {

    public boolean processPayment(String orderId) {
        log.info("[결제 처리] 주문 ID: {}", orderId);
        // 결제 처리 로직 구현 (예: 외부 결제 게이트웨이와 통신)
        // 예시: 결제가 성공했다고 가정
        return true;
    }
}