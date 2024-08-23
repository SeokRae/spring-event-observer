package com.example.observer.domain;

public interface OrderState {
    void handleOrder(OrderContext context);
    OrderStatus getStatus();
}