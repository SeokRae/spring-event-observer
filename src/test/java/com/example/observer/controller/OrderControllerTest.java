package com.example.observer.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testCreateOrderSuccessfully() throws Exception {
    // given: 주문 생성 요청을 위한 JSON 데이터를 준비합니다.
    String orderRequestJson = "{\"productId\": \"prod_1\", \"quantity\": 5}";

    // when: POST 요청으로 주문을 생성합니다.
    mockMvc.perform(post("/v1/api/orders")
        .contentType(MediaType.APPLICATION_JSON)
        .content(orderRequestJson))
      .andDo(print()) // 요청과 응답을 콘솔에 출력합니다.
      .andExpect(status().isOk()) // 응답 상태가 200 OK인지 확인합니다.
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.orderId").exists()); // 응답에 orderId가 포함되어 있는지 확인합니다.
  }
}