package com.example.observer.domain;

public enum OrderStatus {
    INITIAL,            // 초기 상태, 주문 생성 전
    ORDER_CREATED,      // 주문이 생성됨
    PAYMENT_COMPLETED,  // 결제가 완료됨
    SHIPPING,           // 배송 준비 중
    DELIVERED,          // 배송 완료
    COMPLETED,          // 주문 완료
    CANCELLED,          // 주문이 취소됨
    RETURNED            // 반품 처리됨
}