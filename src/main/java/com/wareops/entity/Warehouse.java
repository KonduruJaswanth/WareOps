package com.wareops.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "warehouse")
public class Warehouse {

    @Id
    @Column(name = "warehouse_id")
    private int warehouseId;

    private String name;
    private String city;
    private String status;

    public Warehouse() {}

    public Warehouse(int warehouseId, String name, String city, String status) {
        this.warehouseId = warehouseId;
        this.name = name;
        this.city = city;
        this.status = status;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}