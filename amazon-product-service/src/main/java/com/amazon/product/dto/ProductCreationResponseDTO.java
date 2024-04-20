package com.amazon.product.dto;

import com.amazon.product.enums.PersistenceStatus;
import lombok.Data;

@Data
public class ProductCreationResponseDTO {

    private Integer id;
    private PersistenceStatus status;
    private String name;
    private double price;
}
