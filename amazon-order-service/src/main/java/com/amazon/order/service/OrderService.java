package com.amazon.order.service;

import com.amazon.order.dto.OrderRequest;
import com.amazon.order.dto.OrderStatus;
import com.amazon.order.dto.ProductPrice;
//import io.github.resilience4j.retry.annotation.Retry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

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
        String productIdString = request.stream()
                .map(OrderRequest::getProductId)
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        List<ProductPrice> productPrice = getProductPrice(productIdString);
        Double price = computeTotalAmount(request, productPrice);

        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(UUID.randomUUID().toString());
        orderStatus.setBillAmount(price);
        orderStatus.setStatus(com.amazon.order.enums.OrderStatus.UNPAID);
        return orderStatus;
    }

    //@Retry(name = "retryMechanism", fallbackMethod = "recovery")
    public List<ProductPrice> getProductPrice(String productIds) {

        ResponseEntity<List<ProductPrice>> response = null;
        try {
            log.info("Attempting to call Product Service!");
            ParameterizedTypeReference<List<ProductPrice>> responseType = new ParameterizedTypeReference<>() {
            };
            response = restTemplate.exchange(productService, HttpMethod.GET, null, responseType, productIds);
        } catch (RestClientException restClientException) {
            log.error("Received RestClientException while fetching the Product price details. Error Message: {}", restClientException);
            throw restClientException;
        } catch (Exception e) {
            log.error("Exception occurred => {}", e);
            throw e;
        }

        return response.getBody();
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

    /*public List<ProductPrice> recovery(String productIds, RestClientException e) {
        log.error("Rest Client Exception occurred => {}", e);
        return List.of(new ProductPrice(0, 0.0D));
    }*/

}
