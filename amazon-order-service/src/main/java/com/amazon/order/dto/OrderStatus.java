package com.amazon.order.dto;

import lombok.Data;

@Data
public class OrderStatus {
    private String orderId;
    private Double billAmount;
    private com.amazon.order.enums.OrderStatus status;
}
