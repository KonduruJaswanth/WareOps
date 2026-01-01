package com.wareops.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class OrderItem {

    @Id
    private int orderItemId;
    private int orderId;
    private int productId;
    private int qty;
    private double sellingPrice;

    public int getOrderItemId() { return orderItemId; }
    public void setOrderItemId(int orderItemId) { this.orderItemId = orderItemId; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }

    public double getSellingPrice() { return sellingPrice; }
    public void setSellingPrice(double sellingPrice) { this.sellingPrice = sellingPrice; }
}