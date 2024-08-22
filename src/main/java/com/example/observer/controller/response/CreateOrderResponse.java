package com.example.observer.controller.response;

import lombok.Getter;

@Getter
public class CreateOrderResponse {
  private final String orderId;

  public CreateOrderResponse(String orderId) {
    this.orderId = orderId;
  }
}
