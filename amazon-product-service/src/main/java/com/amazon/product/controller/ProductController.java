package com.amazon.product.controller;

import com.amazon.product.configs.aop.ProductLogger;
import com.amazon.product.configs.properties.ApplicationProperties;
import com.amazon.product.dto.ProductCreationResponseDTO;
import com.amazon.product.dto.ProductRequest;
import com.amazon.product.dto.ProductResponse;
import com.amazon.product.enums.PersistenceStatus;
import com.amazon.product.service.ProductService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/amazon/v1")
public class ProductController {

    Logger log = LogManager.getLogger(ProductController.class);

    @Autowired
    private ApplicationProperties properties;

    @Autowired
    private ProductService productService;

    @PostMapping("/products")
    @ProductLogger
    public ResponseEntity<ProductCreationResponseDTO> addProduct(@RequestBody ProductRequest productRequest) {
        ProductCreationResponseDTO addedProduct = productService.addProduct(productRequest);
        HttpStatus httpStatus = addedProduct.getStatus().equals(PersistenceStatus.CREATED) ? HttpStatus.CREATED :
                HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(addedProduct, httpStatus);
    }

    @GetMapping("/products")
    @ProductLogger
    public ResponseEntity<Map<String, Object>> getProducts(HttpServletRequest request,
                                                             @RequestParam(defaultValue = "10") String countInEachPage,
                                                             @RequestParam String pageNumber) {
        log.info("ThreadContextID => {}", request.getAttribute("threadContextId").toString());
        Map<String, Object> products = productService.getProducts(pageNumber, countInEachPage);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/{id}")
    @ProductLogger
    public ResponseEntity<ProductResponse> getProduct(@PathVariable("id") int productId) {
        ProductResponse productDTO = productService.getProduct(productId);
        HttpStatus httpStatus = productDTO.getProduct() != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(productDTO, httpStatus);
    }

    @GetMapping("/env")
    public void getEnvironment() {
        properties.printEnvironment();
    }

}
