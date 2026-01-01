package com.wareops.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class SalesOrder {

    @Id
    private int orderId;
    private int warehouseId;
    private int customerId;
    private LocalDate orderDate;
    private LocalDate promisedDate;
    private String status;

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getWarehouseId() { return warehouseId; }
    public void setWarehouseId(int warehouseId) { this.warehouseId = warehouseId; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }

    public LocalDate getPromisedDate() { return promisedDate; }
    public void setPromisedDate(LocalDate promisedDate) { this.promisedDate = promisedDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}