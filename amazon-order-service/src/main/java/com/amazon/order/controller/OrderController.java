package com.amazon.order.controller;

import com.amazon.order.config.aop.OrderLogger;
import com.amazon.order.dto.OrderRequest;
import com.amazon.order.dto.OrderStatus;
import com.amazon.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/amazon/v1")
public class OrderController {

    @Autowired
    OrderService orderService;

    @OrderLogger
    @PostMapping("/orders")
    public ResponseEntity<OrderStatus> placeOrder(@RequestBody List<OrderRequest> orderRequest) {
        OrderStatus orderStatus = orderService.placeOrder(orderRequest);
        return new ResponseEntity<>(orderStatus, HttpStatus.OK);
    }


}
