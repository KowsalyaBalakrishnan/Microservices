package com.amazon.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRetrievalDTO {
    private Integer id;
    private String name;
    private double price;
}
