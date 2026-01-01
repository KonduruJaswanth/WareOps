package com.wareops.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

    @Id
    private int productId;

    private String sku;
    private String name;
    private String category;
    private double unitPrice;
    private int reorderLevel;
    private String status;

    public Product() {}

    public Product(int productId, String sku, String name,
                   String category, double unitPrice,
                   int reorderLevel, String status) {
        this.productId = productId;
        this.sku = sku;
        this.name = name;
        this.category = category;
        this.unitPrice = unitPrice;
        this.reorderLevel = reorderLevel;
        this.status = status;
    }

    // getters & setters
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }

    public int getReorderLevel() { return reorderLevel; }
    public void setReorderLevel(int reorderLevel) { this.reorderLevel = reorderLevel; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}