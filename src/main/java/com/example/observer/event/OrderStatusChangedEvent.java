package com.example.observer.event;

import com.example.observer.domain.OrderStatus;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OrderStatusChangedEvent extends ApplicationEvent {

  private final String orderId;
  private final OrderStatus oldStatus;
  private final OrderStatus newStatus;

  public OrderStatusChangedEvent(Object source, String orderId, OrderStatus oldStatus, OrderStatus newStatus) {
    super(source);
    this.orderId = orderId;
    this.oldStatus = oldStatus;
    this.newStatus = newStatus;
  }

}