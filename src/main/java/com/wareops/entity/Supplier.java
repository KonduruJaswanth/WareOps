package com.wareops.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "supplier")
public class Supplier {

    @Id
    private int supplierId;

    private String name;
    private String gstNumber;
    private String phone;
    private String city;
    private String status;

    public Supplier() {}

    public Supplier(int supplierId, String name, String gstNumber,
                    String phone, String city, String status) {
        this.supplierId = supplierId;
        this.name = name;
        this.gstNumber = gstNumber;
        this.phone = phone;
        this.city = city;
        this.status = status;
    }

    // getters & setters
    public int getSupplierId() { return supplierId; }
    public void setSupplierId(int supplierId) { this.supplierId = supplierId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGstNumber() { return gstNumber; }
    public void setGstNumber(String gstNumber) { this.gstNumber = gstNumber; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}