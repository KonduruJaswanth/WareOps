package com.wareops.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Bin {

    @Id
    private int binId;
    private int warehouseId;
    private String code;
    private String zone;
    private int capacity;
    private String status;

    public int getBinId() { return binId; }
    public void setBinId(int binId) { this.binId = binId; }

    public int getWarehouseId() { return warehouseId; }
    public void setWarehouseId(int warehouseId) { this.warehouseId = warehouseId; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getZone() { return zone; }
    public void setZone(String zone) { this.zone = zone; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}