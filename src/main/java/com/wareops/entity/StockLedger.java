package com.wareops.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class StockLedger {

    @Id
    private int ledgerId;
    private int warehouseId;
    private int productId;
    private LocalDate movementDate;
    private String movementType;
    private int qty;
    private String refType;
    private int refId;

    public int getLedgerId() { return ledgerId; }
    public void setLedgerId(int ledgerId) { this.ledgerId = ledgerId; }

    public int getWarehouseId() { return warehouseId; }
    public void setWarehouseId(int warehouseId) { this.warehouseId = warehouseId; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public LocalDate getMovementDate() { return movementDate; }
    public void setMovementDate(LocalDate movementDate) { this.movementDate = movementDate; }

    public String getMovementType() { return movementType; }
    public void setMovementType(String movementType) { this.movementType = movementType; }

    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }

    public String getRefType() { return refType; }
    public void setRefType(String refType) { this.refType = refType; }

    public int getRefId() { return refId; }
    public void setRefId(int refId) { this.refId = refId; }
}