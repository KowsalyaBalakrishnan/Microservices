package com.amazon.product.dto;

public class ProductPrice {

    private Integer productId;
    private Double productPrice;

    public ProductPrice(Integer productId, Double productPrice) {
        this.productId = productId;
        this.productPrice = productPrice;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }
}
