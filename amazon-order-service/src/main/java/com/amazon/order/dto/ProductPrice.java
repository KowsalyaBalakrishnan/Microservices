package com.amazon.order.dto;

public class ProductPrice {

    private Integer productId;
    private Double productPrice;

    public ProductPrice() {
    }

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

    @Override
    public String toString() {
        return "ProductPrice{" +
                "productId=" + productId +
                ", productPrice=" + productPrice +
                '}';
    }
}
