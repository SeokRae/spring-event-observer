package com.example.observer.domain;

import lombok.Getter;
import org.springframework.context.ApplicationEventPublisher;

public class OrderContext {
    @Getter
    private final ApplicationEventPublisher eventPublisher;

    private OrderState currentState;

    public OrderContext(OrderState initialState, ApplicationEventPublisher eventPublisher) {
        this.currentState = initialState;
        this.eventPublisher = eventPublisher;
    }

    public void setState(OrderState state) {
        this.currentState = state;
    }
}