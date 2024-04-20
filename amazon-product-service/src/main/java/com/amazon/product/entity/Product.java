package com.amazon.product.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @Column(name = "P_ID")
    private Integer id;

    @Column(name = "PRODUCTNAME")
    private String product;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "BRAND_ID")
    private Brand brand;

    @Column(name = "MODELNAME")
    private String model;

    @Column(name = "PRICE")
    private double price;
}
