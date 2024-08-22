package com.example.observer.controller.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OrderCreateRequest {
  private final String productId;
  private final int quantity;

  public OrderCreateRequest(String productId, int quantity) {
    this.productId = productId;
    this.quantity = quantity;
  }
}
