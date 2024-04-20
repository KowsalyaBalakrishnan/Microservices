package com.amazon.product.service;

import com.amazon.product.database.ProductRepository;
import com.amazon.product.dto.ProductCreationResponseDTO;
import com.amazon.product.dto.ProductRequest;
import com.amazon.product.dto.ProductResponse;
import com.amazon.product.entity.Product;
import com.amazon.product.enums.PersistenceStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService {

    Logger log = LogManager.getLogger(ProductService.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductRepository productRepository;

    public ProductCreationResponseDTO addProduct(ProductRequest product) {
        log.info("Adding Product");
        Product productData = modelMapper.map(product, Product.class);
        Product savedProduct = productRepository.save(productData);
        ProductCreationResponseDTO productResponse = modelMapper.map(savedProduct, ProductCreationResponseDTO.class);
        PersistenceStatus persistenceStatus = savedProduct.getId() != null ?
                PersistenceStatus.CREATED : PersistenceStatus.DB_ERROR;
        productResponse.setStatus(persistenceStatus);
        return productResponse;
    }

    @Cacheable(value = "allProducts")
    public Map<String, Object> getProducts(String pageNumber, String countInEachPage) {
        log.info("ThreadContext => {}", ThreadContext.get("requestId"));
        log.info("Fetching All the Products from database...");

        // Creating Pagination
        Pageable pageable = PageRequest.of(
                Integer.parseInt(pageNumber) - 1, Integer.parseInt(countInEachPage) // Page starts from 0 so subtract 1
                , Sort.by(Sort.Order.asc("PRODUCTNAME"))
        );
        Page<Product> products = productRepository.getAllProducts(pageable);
        Map<String, Object> response = convertPageResponse(products);

        log.info("Products Retrieved");
        return response;
    }

    private Map<String, Object> convertPageResponse(Page<Product> products) {
        List<Product> productList = products.getContent();
        int currentPage = products.getNumber();
        long totalProductsCount = products.getTotalElements();
        int totalPages = products.getTotalPages();

        List<ProductResponse> list = productList.stream()
                .map(ProductService::getProductResponse)
                .collect(Collectors.toList());

        Map<String, Object> productResponse = new HashMap<>();
        productResponse.put("products", list);
        productResponse.put("totalItems", totalProductsCount);
        productResponse.put("renderedItems", list.size());
        productResponse.put("currentPage", currentPage + 1);    // Page starts from 0 so add 1
        productResponse.put("totalPages", totalPages);

        return productResponse;
    }


    public ProductResponse getProduct(int productId) {
        log.info("Retrieving one Produce based on ID");
        Product product = productRepository.findById(productId).orElse(new Product());
        return getProductResponse(product);
    }

    private static ProductResponse getProductResponse(Product product) {
        return ProductResponse.getBuilder()
                .productId(product.getId())
                .productName(product.getProduct())
                .brand(product.getBrand().getName())
                .model(product.getModel())
                .price(product.getPrice())
                .build();
    }
}
