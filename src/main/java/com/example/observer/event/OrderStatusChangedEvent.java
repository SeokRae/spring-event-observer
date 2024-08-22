package com.example.observer.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OrderStatusChangedEvent extends ApplicationEvent {

  private final String orderId;
  private final String oldStatus;
  private final String newStatus;

  public OrderStatusChangedEvent(Object source, String orderId, String oldStatus, String newStatus) {
    super(source);
    this.orderId = orderId;
    this.oldStatus = oldStatus;
    this.newStatus = newStatus;
  }

}