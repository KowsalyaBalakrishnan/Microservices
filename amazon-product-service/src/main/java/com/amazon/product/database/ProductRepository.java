package com.amazon.product.database;

import com.amazon.product.dto.ProductPrice;
import com.amazon.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "SELECT * FROM PRODUCT", nativeQuery = true)
    Page<Product> getAllProducts(Pageable pageable);

    @Query(value = "SELECT P_ID as productId, PRICE as productPrice FROM PRODUCT WHERE P_ID IN (:id)",
            nativeQuery = true)
    List<Map<String, Object>> getProductsPrice(@Param("id") String[] productIds);
}
