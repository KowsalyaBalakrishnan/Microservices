package com.amazon.product.service;

import com.amazon.product.configs.aop.ProductLogger;
import com.amazon.product.database.ProductRepository;
import com.amazon.product.dto.ProductCreationResponseDTO;
import com.amazon.product.dto.ProductRequestDTO;
import com.amazon.product.dto.ProductRetrievalDTO;
import com.amazon.product.entity.Product;
import com.amazon.product.enums.PersistenceStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    Logger log = LogManager.getLogger(ProductService.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductRepository productRepository;

    @ProductLogger
    public ProductCreationResponseDTO addProduct(ProductRequestDTO product) {
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
    @ProductLogger
    public List<ProductRetrievalDTO> getProducts() throws Exception{
        log.info("Fetching All the Products from database...");
        Thread.sleep(2000);
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> modelMapper.map(product, ProductRetrievalDTO.class))
                .collect(Collectors.toList());
    }

    @ProductLogger
    public ProductRetrievalDTO getProduct(int productId) {
        log.info("Retrieving one Produce based on ID");
        Product product = productRepository.findById(productId).orElse(new Product());
        return modelMapper.map(product, ProductRetrievalDTO.class);
    }
}
