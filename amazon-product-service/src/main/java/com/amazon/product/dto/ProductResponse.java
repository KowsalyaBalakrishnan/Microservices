package com.amazon.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ProductResponse {
    private Integer id;
    private String product;
    private String brand;
    private String model;
    private double price;

    private void setId(Integer id) {
        this.id = id;
    }

    private void setProduct(String product) {
        this.product = product;
    }

    private void setBrand(String brand) {
        this.brand = brand;
    }

    private void setModel(String model) {
        this.model = model;
    }

    private void setPrice(double price) {
        this.price = price;
    }

    public static ProductBuilder getBuilder() {
        return new ProductBuilder();
    }

    public static class ProductBuilder {
        private Integer productId;
        private String productName;
        private String brandName;
        private String modelName;
        private double price;

        public ProductBuilder productId(Integer productId) {
            this.productId = productId;
            return this;
        }

        public ProductBuilder productName(String productName) {
            this.productName = productName;
            return this;
        }

        public ProductBuilder brand(String brandName) {
            this.brandName = brandName;
            return this;
        }

        public ProductBuilder model(String modelName) {
            this.modelName = modelName;
            return this;
        }

        public ProductBuilder price(double price) {
            this.price = price;
            return this;
        }

        public ProductResponse build() {
            ProductResponse productResponse = new ProductResponse();
            productResponse.setId(this.productId);
            productResponse.setProduct(this.productName);
            productResponse.setBrand(this.brandName);
            productResponse.setModel(this.modelName);
            productResponse.setPrice(this.price);
            return productResponse;
        }
    }
}
