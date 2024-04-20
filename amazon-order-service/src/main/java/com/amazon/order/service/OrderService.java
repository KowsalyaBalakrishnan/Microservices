package com.amazon.order.service;

import com.amazon.order.dto.OrderRequest;
import com.amazon.order.dto.OrderStatus;
import com.amazon.order.dto.ProductPrice;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    Logger log = LogManager.getLogger(OrderService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${product.service.uri}")
    private String productService;

    public OrderStatus placeOrder(List<OrderRequest> request) {
        List<ProductPrice> productPrice = getProductPrice(request);
        Double price = computeTotalAmount(request, productPrice);

        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(UUID.randomUUID().toString());
        orderStatus.setBillAmount(price);
        orderStatus.setStatus(com.amazon.order.enums.OrderStatus.UNPAID);
        return orderStatus;
    }

    private List<ProductPrice> getProductPrice(List<OrderRequest> request) {
        String productIdString = request.stream()
                .map(OrderRequest::getProductId)
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        ResponseEntity<List<ProductPrice>> response = null;
        try {
            ParameterizedTypeReference<List<ProductPrice>> responseType = new ParameterizedTypeReference<>() {
            };
            response = restTemplate.exchange(
                    productService,
                    HttpMethod.GET,
                    null,
                    responseType,
                    productIdString);
        } catch (Exception e) {
            log.error(e);
        }

        if (response != null) {
            return response.getBody();
        }
        return null;
    }

    private Double computeTotalAmount(List<OrderRequest> orderRequests, List<ProductPrice> productPrice) {

        double totalAmount = 0;
        try {
            for (OrderRequest order : orderRequests) {
                Integer productId = order.getProductId();
                Integer quantity = order.getQuantity();
                for (ProductPrice prod : productPrice) {
                    if (prod.getProductId() == productId) {
                        double unitPrice = prod.getProductPrice();
                        log.info("Unit Price = {}", unitPrice);
                        totalAmount = totalAmount + (unitPrice * quantity);
                    }
                }
            }
            log.info("Total Amount for the Order Request => {}", totalAmount);
        } catch (Exception e) {
            log.error(e);
        }

        return totalAmount;
    }

}
